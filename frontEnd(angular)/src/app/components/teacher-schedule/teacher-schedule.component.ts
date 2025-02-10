import { Component, OnInit } from '@angular/core';
import { TeacherTimeTableService } from 'src/app/services/teachertimetable.service';
import { TeachersTimeTable } from 'src/app/models/teachertimetables.models';
@Component({
  selector: 'app-teacher-schedule',
  templateUrl: './teacher-schedule.component.html',
  styleUrls: ['./teacher-schedule.component.css']
})
export class TeacherScheduleComponent implements OnInit {
  timetables: TeachersTimeTable[] = [];
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

  constructor(private teacherTimeTableService: TeacherTimeTableService) { }

  ngOnInit(): void {
    this.teacherTimeTableService.getAllTimeTables().subscribe(data => {
      this.timetables = data;
    });
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
}