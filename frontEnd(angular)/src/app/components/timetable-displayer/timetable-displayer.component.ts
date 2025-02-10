import { Component, OnInit } from '@angular/core';
import { format } from 'date-fns';
import { Classes } from 'src/app/models/classes.models';
import { RawData, Slot } from 'src/app/models/rawData.models';
import { Room } from 'src/app/models/rooms.models';
import { TeacherDto } from 'src/app/models/teacherD.models';
import { DaySchedule, ScheduleSlot, Timetable } from 'src/app/models/timetable.models';
import { ScheduleService } from 'src/app/services/Schedule.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-timetable-displayer',
  templateUrl: './timetable-displayer.component.html',
  styleUrls: ['./timetable-displayer.component.css']
})
export class TimetableDisplayerComponent implements OnInit {
  mondays: string[];
  selectedMonday: string;
    timeTables: Timetable[] = [];
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
  

  constructor(private timeTableService:ScheduleService,) {
    this.mondays = this.getUpcomingMondays();
    this.selectedMonday = this.mondays[0];
    
  }
  ngOnInit(): void {
    this.generate()
  }

  generateSchedule(){
    
    this.timeTableService.generateSchedule(this.selectedMonday).subscribe(
      (data) => {

        console.log(data)
        this.timeTables = data;

        // Transform the scheduleMap into RawData format
        this.rawData = this.timeTables.map(timetable => {
          const rawDataEntry: RawData = {
            startDate: timetable.startDate,
            classEntity: timetable.classEntity,
            scheduleMap: {}
          };

          Object.keys(timetable.scheduleMap).forEach(date => {
            const daySchedule = timetable.scheduleMap[date];
            const slots: Slot[] = daySchedule.slots.map(slot => ({
              teacher:slot.teacher,
              room:slot.room,
              time: slot.time,
              durationIndex: slot.durationIndex,
              subject: {
                id: slot.subject.id,
                name: slot.subject.name,
                sessionDurations: slot.subject.sessionDurations
              },
             
            }));

            rawDataEntry.scheduleMap[date] = {
              date: date,
              slots: slots
            };
          });

          return rawDataEntry;
        });

        console.log(this.rawData);
        this.generateTimetables();
      },
      (error) => {
        console.error('Error in generate:', error);
      }
    );
  
  }

  handlegenrate(){
    this.generateSchedule()
  }


   handleRegenrate(): void {
      Swal.fire({
        title: 'Are you sure?',
        text: "You won't be able to go back!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, Regenrate TimeTable!'
      }).then((result) => {
        if (result.isConfirmed) {
          console.log("hi")
          this.generateSchedule()
        }
      });
    }

  getUpcomingMondays(): string[] {
    const today = new Date();
    const dayOfWeek = today.getDay(); // 0 = Sunday, 1 = Monday, etc.

    // Get this week's Monday
    const thisMonday = new Date();
    thisMonday.setDate(today.getDate() - dayOfWeek + (dayOfWeek === 0 ? -6 : 1));

    // Get next week's Monday
    const nextMonday = new Date(thisMonday);
    nextMonday.setDate(thisMonday.getDate() + 7);

    return [format(thisMonday, 'yyyy-MM-dd'), format(nextMonday, 'yyyy-MM-dd')];
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


    generate() {
     
      this.timeTableService.getTimetablesByStartDate(this.selectedMonday).subscribe(
        (data) => {
  
          console.log(data)
          this.timeTables = data;
  
          // Transform the scheduleMap into RawData format
          this.rawData = this.timeTables.map(timetable => {
            const rawDataEntry: RawData = {
              startDate: timetable.startDate,
              classEntity: timetable.classEntity,
              scheduleMap: {}
            };
  
            Object.keys(timetable.scheduleMap).forEach(date => {
              const daySchedule = timetable.scheduleMap[date];
              const slots: Slot[] = daySchedule.slots.map(slot => ({
                teacher:slot.teacher,
                room:slot.room,
                time: slot.time,
                durationIndex: slot.durationIndex,
                subject: {
                  id: slot.subject.id,
                  name: slot.subject.name,
                  sessionDurations: slot.subject.sessionDurations
                },
               
              }));
  
              rawDataEntry.scheduleMap[date] = {
                date: date,
                slots: slots
              };
            });
  
            return rawDataEntry;
          });
  
          console.log(this.rawData);
          this.generateTimetables();
        },
        (error) => {
          console.error('Error in generate:', error);
        }
      );
    }
  
    calculateEndDate(startDate: string): string {
      const date = new Date(startDate);
      date.setDate(date.getDate() + 6); // Add 6 days to the startDate
      return date.toISOString(); // Return the endDate in ISO format
    }



}
