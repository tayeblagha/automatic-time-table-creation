import { Classes } from "./classes.models";
import { Room } from "./rooms.models";
import { Subject } from "./subject.models";
import { TeacherDto } from "./teacherD.models";
export interface ScheduleSlot {
    id: number;
    time: string; // ISO string format for time (e.g., "2025-01-24T09:00:00")
    endTime:string
    durationIndex: number;
    subjectId: number;
    teacherId: number;
    roomId: number;
    subject: Subject;  // This will hold the full Subject object
    teacher: TeacherDto;  // This will hold the full TeacherDto object
    room: Room;  // This will hold the full Classroom object
  }

  export interface DaySchedule {
    id: number;
    date: string; // ISO string format (e.g., "2025-01-24")
    slots: ScheduleSlot[];
  }

export interface Timetable {
  id: number;
  scheduleMap: { [date: string]: DaySchedule };
  startDate: string; // ISO string format (e.g., "2025-01-24")
  classEntity: Classes;
}
