import { Component, OnInit } from '@angular/core';
import { ScheduleService } from 'src/app/services/Schedule.service';
import { jsPDF } from 'jspdf';
import html2canvas from 'html2canvas';
import { RawData, Slot } from 'src/app/models/rawData.models';
import { ModuleElement } from 'src/app/models/moduleElement.models';
import { SchoolGeneralInfoService } from 'src/app/services/school-general-info.service';
import { SemesterService } from 'src/app/services/semester.service';
import { ActionsService } from 'src/app/services/actions.service';
import { CookieService } from 'ngx-cookie-service';
import { DepartmentService } from 'src/app/services/department.service';
import { SchoolGeneralInfo } from 'src/app/models/school.models';
import { Semestre } from 'src/app/models/semestre.models';
import { Department } from 'src/app/models/departement.models';
import { DaySchedule, ScheduleSlot, Timetable } from 'src/app/models/timetable.models';
import { Classes } from 'src/app/models/classes.models';
import { TeacherDto } from 'src/app/models/teacherD.models';
import { Room } from 'src/app/models/rooms.models';

@Component({
  selector: 'app-schedule',
  templateUrl: './schedule.component.html',
  styleUrls: ['./schedule.component.css'],
})
export class ScheduleComponent implements OnInit {
  timeTables: Timetable[] = [];
  grade_id: any;
  department_id: any;
  semester_id: any;
  is_generated = false;
  prof!: boolean;
  admin: boolean = false;
  public moduleElement: ModuleElement[] = [];
  ready = false;
  departments: Department[] = [];

  schoolGeneralInfo: SchoolGeneralInfo | undefined;
  semesters: Semestre[] | undefined = [];
  grades: number[] = [1, 2, 3, 4, 5];
  selectedGrade: any;
  rawData: RawData[] = [];
  timetables: {
    scheduleMap: { [date: string]: DaySchedule };
    startDate: string; // ISO string format (e.g., "2025-01-24")
    classEntity: Classes;
    data: { day: string; slots: { name: string; span: number; teacher?: TeacherDto,room?:Room }[] }[];
  }[] = [];
  timeSlots = [
    '8-9',
    '9-10',
    '10-11',
    '11-12',
    '12-14 (Break)',
    '14-15',
    '15-16',
    '16-17',
    '17-18',
  ];

  constructor(
    private timeTableService: ScheduleService,
    private schoolGeneralInfoService: SchoolGeneralInfoService,
    private semesterService: SemesterService,
    private actons: ActionsService,
    private cookieService: CookieService,
    private departmentService: DepartmentService,
    private scheduleService: ScheduleService,
    private departementService: DepartmentService
  ) {}

  ngOnInit() {
    this.loadSchoolData();
    this.getDepartements();
    this.generateTimetables();
    this.prof = this.cookieService.get('role') == 'Teacher' ? true : false;
    if (this.prof) {
      this.ready = true;
    } else {
      this.admin = this.cookieService.check('role');
    }
  }

  generateTimetables() {
    this.timetables = this.timeTables.map((timetable, index) => {
      const startDate = new Date(timetable.startDate);
      const endDate = new Date(startDate);
      endDate.setDate(startDate.getDate() + 6); // Calculate endDate (startDate + 6 days)
  
      return {
        id: timetable.id,
        startDate: timetable.startDate,
        endDate: endDate.toISOString(), // Add endDate
        classEntity: timetable.classEntity, // Add classEntity
        scheduleMap: timetable.scheduleMap, // Add scheduleMap
        data: this.organizeTimetableData(timetable.scheduleMap) // Organize timetable data
      };
    });
  }
  
  organizeTimetableData(scheduleMap: { [date: string]: DaySchedule }) {
    const daysOfWeek = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
    const organizedData: Record<string, Record<string, { name: string; span: number; teacher?: TeacherDto,room?:Room }>> = {};
  
    Object.keys(scheduleMap).forEach((dateKey) => {
      const day = new Date(dateKey).toLocaleDateString('en-US', { weekday: 'long' });
  
      if (!organizedData[day]) {
        organizedData[day] = {};
      }
  
      scheduleMap[dateKey].slots.forEach((slot: ScheduleSlot) => {
        const startHour = parseInt(slot.time.split(':')[0], 10);
        const duration = slot.subject.sessionDurations[slot.durationIndex] || 1;
  
        const timeSlotIndex = this.timeSlots.findIndex((slotRange) =>
          slotRange.startsWith(startHour.toString())
        );
  
        if (timeSlotIndex !== -1) {
          const timeSlot = this.timeSlots[timeSlotIndex];
          organizedData[day][timeSlot] = {
            name: slot.subject.name,
            span: duration,
            teacher: slot.teacher,
            room:slot.room // Include the teacher information
          };
  
          for (let i = 1; i < duration; i++) {
            const nextSlot = this.timeSlots[timeSlotIndex + i];
            if (nextSlot) {
              organizedData[day][nextSlot] = { name: '', span: 0 };
            }
          }
        }
      });
    });
  
    return daysOfWeek.map((day) => {
      const dayData = organizedData[day] || {};
      return {
        day,
        slots: this.timeSlots.map((slot) => dayData[slot] || { name: '', span: 1 }),
      };
    });
  }

  downloadPDF() {
    const pdf = new jsPDF('landscape');
    const pageWidth = pdf.internal.pageSize.width;
    const pageHeight = pdf.internal.pageSize.height;
    const margin = 10; // Margins for the page
    const contentWidth = pageWidth - 2 * margin; // Full width for one timetable
    const contentHeight = (pageHeight - 4 * margin) / 2; // Half the page height for one timetable

    let yOffset = margin; // Start from the top margin
    let tableIndex = 1; // Start indexing from 1
    let pageCount = 1; // Start with page 1

    const addTimetableToPDF = (timetableIndex: number) => {
      if (timetableIndex >= this.timetables.length) {
        // All timetables processed, save the PDF
        pdf.save('timetables.pdf');
        return;
      }

      const elementId = `timetable-${timetableIndex}`;
      const element = document.getElementById(elementId);

      if (!element) {
        console.error(`Element with ID ${elementId} not found.`);
        return;
      }

      html2canvas(element).then((canvas) => {
        const imgData = canvas.toDataURL('image/png');
        const imgHeight = (canvas.height * contentWidth) / canvas.width;

        // Scale to fit within content height
        const scaledHeight = Math.min(imgHeight, contentHeight);

        // Add title with the correct index
        pdf.setFontSize(14);
        pdf.text(`Timetable ${tableIndex}`, margin, yOffset - 5);

        // Add timetable image
        pdf.addImage(imgData, 'PNG', margin, yOffset, contentWidth, scaledHeight);

        // Update Y offset for the next timetable
        yOffset += contentHeight + margin;
        tableIndex++;

        // Add footer
        pdf.setFontSize(10);
        pdf.text(`Page ${pageCount}`, pageWidth - margin - 20, pageHeight - margin);

        // Add a new page after two timetables
        if ((tableIndex - 1) % 2 === 0 && timetableIndex < this.timetables.length - 1) {
          pdf.addPage();
          pageCount++;
          yOffset = margin; // Reset Y offset for the new page
        }

        // Process the next timetable
        addTimetableToPDF(timetableIndex + 1);
      }).catch((error) => {
        console.error('Error generating timetable PDF:', error);
      });
    };

    // Start processing timetables
    addTimetableToPDF(0);
  }

  async loadSchoolData(): Promise<void> {
    await this.getSchoolGeneralInfo();
    this.getSemesters();
  }

  async getSchoolGeneralInfo(): Promise<void> {
    try {
      const data = await this.schoolGeneralInfoService.getSchoolGeneralInfo().toPromise();
      this.schoolGeneralInfo = data;
    } catch (error) {
      console.error('Error fetching school general info', error);
    }
  }

  async getSemesters(): Promise<void> {
    if (this.schoolGeneralInfo?.numberSemesters) {
      try {
        const data = await this.semesterService.getFirstNSemesters(Number(this.schoolGeneralInfo.numberSemesters)).toPromise();
        this.semesters = data;
      } catch (error) {
        console.error('Error fetching semesters', error);
      }
    }
  }

  getDepartements() {
    this.departementService.getDepartements().subscribe((data: Department[]) => {
      this.departments = data;
    });
  }

  generate() {
    this.is_generated = true;
    // this.timeTableService.generateSchedule(this.grade_id, this.department_id, this.semester_id).subscribe(
    //   (data) => {

    //     console.log(data)
    //     this.timeTables = data;

    //     // Transform the scheduleMap into RawData format
    //     this.rawData = this.timeTables.map(timetable => {
    //       const rawDataEntry: RawData = {
    //         startDate: timetable.startDate,
    //         classEntity: timetable.classEntity,
    //         scheduleMap: {}
    //       };

    //       Object.keys(timetable.scheduleMap).forEach(date => {
    //         const daySchedule = timetable.scheduleMap[date];
    //         const slots: Slot[] = daySchedule.slots.map(slot => ({
    //           teacher:slot.teacher,
    //           room:slot.room,
    //           time: slot.time,
    //           durationIndex: slot.durationIndex,
    //           subject: {
    //             id: slot.subject.id,
    //             name: slot.subject.name,
    //             sessionDurations: slot.subject.sessionDurations
    //           },
             
    //         }));

    //         rawDataEntry.scheduleMap[date] = {
    //           date: date,
    //           slots: slots
    //         };
    //       });

    //       return rawDataEntry;
    //     });

    //     console.log(this.rawData);
    //     this.generateTimetables();
    //   },
    //   (error) => {
    //     console.error('Error in generate:', error);
    //   }
    // );
  }

  calculateEndDate(startDate: string): string {
    const date = new Date(startDate);
    date.setDate(date.getDate() + 6); // Add 6 days to the startDate
    return date.toISOString(); // Return the endDate in ISO format
  }

  
}