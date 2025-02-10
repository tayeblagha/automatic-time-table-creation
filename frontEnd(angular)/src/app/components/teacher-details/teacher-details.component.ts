import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DayTimeDto } from 'src/app/models/daytimedto.models';
import { Prof } from 'src/app/models/prof.models';
import { TeachersTimeTable } from 'src/app/models/teachertimetables.models';
import { ProfServiceService } from 'src/app/services/prof-service.service';
import { TeacherTimeTableService } from 'src/app/services/teachertimetable.service';

@Component({
  selector: 'app-teacher-details',
  templateUrl: './teacher-details.component.html',
  styleUrls: ['./teacher-details.component.css']
})
export class TeacherDetailsComponent implements OnInit {
  teacher_id:any
  timetable: TeachersTimeTable |undefined;
  daytimedto:DayTimeDto| undefined
  newTeacher:Prof|undefined
  timeSlots = [
      { start: '08:00:00', label: '8-9' },
      { start: '09:00:00', label: '9-10' },
      { start: '10:00:00', label: '10-11' },
      { start: '11:00:00', label: '11-12' },
      { label: '12-14 (Break)' },
      { start: '14:00:00', label: '14-15' },
      { start: '15:00:00', label: '15-16' },
      { start: '16:00:00', label: '16-17' },
      { start: '17:00:00', label: '17-18' },
    ];
  
   
  constructor(  private activatedRoute:ActivatedRoute,
                private teacherService:ProfServiceService,
                private teacherTimeTableService: TeacherTimeTableService,
  ){

  }
  ngOnInit(): void {
    this.teacher_id=this.activatedRoute.snapshot.params["teacher_id"]
    if (this.teacher_id){
      this.teacherService.getProf(this.teacher_id).subscribe(
        data=>{
          this.newTeacher=data
          this.teacherTimeTableService.getCurrentTimeTable(Number(this.newTeacher.id)).subscribe(
            data=>{
              this.timetable=data
              this.getCurrentSlot()
            }
          )
        })   }
  }





  

  generateDays(startDate: string): any[] {
    const days = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
    const start = new Date(startDate + 'T00:00:00Z');
    return days.map((name, index) => ({
      name,
      date: new Date(start.getTime() + (index * 86400000)).toISOString().split('T')[0]
    }));
  }

  processTimetable(timetable: TeachersTimeTable): any[] {
    return this.generateDays(timetable.startDate).map(day => ({
      ...day,
      slots: this.mergeConsecutiveSlots(day, timetable)
    }));
  }
  
  private mergeConsecutiveSlots(day: any, timetable: TeachersTimeTable): any[] {
    const mergedSlots = [];
    let currentIndex = 0;
    const slots = this.timeSlots.map(slot => ({
      ...slot,
      session: slot.start ? timetable.dayTimes.find(dt => 
        dt.date === day.date && dt.time === slot.start
      ) : null
    }));
  
    while (currentIndex < slots.length) {
      const currentSlot = slots[currentIndex];
      
      // Handle break or empty slots
      if (!currentSlot.start || !currentSlot.session) {
        mergedSlots.push({ ...currentSlot, colspan: 1 });
        currentIndex++;
        continue;
      }
  
      let colspan = 1;
      let nextIndex = currentIndex + 1;
  
      // Merge consecutive slots with the same class and room
      while (nextIndex < slots.length) {
        const nextSlot = slots[nextIndex];
        if (!nextSlot.start) break; // Stop at break slots
  
        const nextSession = nextSlot.session;
        if (nextSession && 
            nextSession.classes.id === currentSlot.session.classes.id ) {
          colspan++;
          nextIndex++;
        } else {
          break;
        }
      }
  
      mergedSlots.push({ ...currentSlot, colspan });
      currentIndex = nextIndex;
    }
  
    return mergedSlots;
  }

  getCurrentSlot(){
    return this.teacherTimeTableService.getCurrentSlot(this.teacher_id).subscribe(
      data=>{
        this.daytimedto=data
      }
    )
  }
  

  calculateEndDate(startDate: string): string {
    const date = new Date(startDate);
    date.setDate(date.getDate() + 6); // Add 6 days to the startDate
    return date.toISOString(); // Return the endDate in ISO format
  }

}
