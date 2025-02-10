import { Classes } from "./classes.models";
import { Major } from "./majors.models";
import { Room } from "./rooms.models";

export interface DayTimeDto {
    startTime: string; // ISO time format (e.g., '12:30:00')
    endTime: string;   // ISO time format (e.g., '14:00:00')
    roomIndex?: number;
        room?:Room,
        classIndex?: number;
        classes:Classes;
        major:Major;
        majorIndex:any;
  }
  