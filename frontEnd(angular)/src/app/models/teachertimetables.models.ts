import { Classes } from "./classes.models";
import { Major } from "./majors.models";
import { Prof } from "./prof.models";
import { Room } from "./rooms.models";

export interface TeachersTimeTable {
    startDate: string; // Using string to represent LocalDate
    teacher: Prof;
    dayTimes: DayTime[];

  }
  
  
  
  export interface DayTime {
    id: number;
    date: string; // Using string to represent LocalDate
    time: string; // Using string to represent LocalTime
    roomIndex?: number;
    room?:Room,
    classIndex?: number;
    classes:Classes;
    teachersTimeTable?: TeachersTimeTable;
    major:Major;
    majorIndex:any;
  }
  