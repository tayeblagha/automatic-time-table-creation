import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Classes } from 'src/app/models/classes.models';
import { RawData, Slot } from 'src/app/models/rawData.models';
import { Room } from 'src/app/models/rooms.models';
import { Student } from 'src/app/models/student.models';
import { TeacherDto } from 'src/app/models/teacherD.models';
import { DaySchedule, ScheduleSlot, Timetable } from 'src/app/models/timetable.models';
import { ScheduleService } from 'src/app/services/Schedule.service';
import { StudentService } from 'src/app/services/student.service ';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {
  endTime:string=""
  newStudent:Student |undefined
  student_id:any
  timeTables: Timetable[] = [];
  currentscheduleSlot:ScheduleSlot|undefined

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
    private activatedRoute:ActivatedRoute,
    private studentService:StudentService,
    private timeTableService: ScheduleService,
  ){

  }

  ngOnInit(): void {
    this.student_id=this.activatedRoute.snapshot.params["student_id"]
    if (this.student_id){
      this.studentService.getStudentById(this.student_id).subscribe(
        data=>{
          this.newStudent=data
          this.searchSlot()
          this.generate()
        })   }

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
       if(this.newStudent?.classes?.id){
        this.timeTableService.getCurrentTimetableById(this.newStudent?.classes.id).subscribe(
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
    
        console.log(this.timetables);
       }
       
     }
   
     calculateEndDate(startDate: string): string {
       const date = new Date(startDate);
       date.setDate(date.getDate() + 6); // Add 6 days to the startDate
       return date.toISOString(); // Return the endDate in ISO format
     }


     searchSlot(){
      if (this.newStudent?.classes?.id) {
       this.timeTableService.searchslot(this.newStudent?.classes?.id,Number(this.newStudent.classes.grade)).subscribe(
        data=>{
          this.currentscheduleSlot=data
          
         
        }
      )
     }
    }

    // getEndTime(time: string, slot: ScheduleSlot): string {
    //   if (!slot.subject || !slot.subject.sessionDurations || slot.durationIndex === undefined) {
       
    //   }
      
    //   const duration = slot.subject.sessionDurations[slot.durationIndex];
    //   if (duration === undefined) {
    //   }
      
    //   const [hours, minutes, seconds] = time.split(":").map(Number);
    //   const date = new Date();
    //   date.setHours(hours+ duration, minutes , seconds);
      
    //   return date.toTimeString().split(" ")[0]; // Returns HH:MM:SS format
    // }

    
    
    
   
}
