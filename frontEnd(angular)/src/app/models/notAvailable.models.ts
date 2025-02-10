import { Prof } from "./prof.models";

export interface NotAvailable {
    id:        number;
    day:      string;
    teacher: Prof;
    period:   string;
}