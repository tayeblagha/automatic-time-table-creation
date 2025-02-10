import { Classes } from "./classes.models";
import { Prof } from "./prof.models";
import { Room } from "./rooms.models";
import { TeacherDto } from "./teacherD.models";

export interface Subject {
  id: number;
  name: string;
  sessionDurations: number[]; // Array of possible session durations
}

export interface Slot {
  time: string;
  durationIndex: number; // Index to indicate the session duration
  subject: Subject;
  teacher:TeacherDto
  room:Room
}

export interface DayData {
  date: string;
  slots: Slot[];
}

export interface RawData {
  startDate: string; // ISO string format (e.g., "2025-01-24")
  classEntity: Classes;
  scheduleMap: Record<string, DayData>;
}