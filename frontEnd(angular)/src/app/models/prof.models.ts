import { Department } from "./departement.models";
import { Major } from "./majors.models";

export interface Prof {
    id?:         number;
    lastName:   string;
    firstName:  string;
    email:      string;
    login:      string;
    password:   string;
    maxHoursPerWeek:number,
    departments?:Department[]
    majors:Major[],
    reservedHours:number

}
export type TeacherHashMap = { [major_id: number]: Prof };