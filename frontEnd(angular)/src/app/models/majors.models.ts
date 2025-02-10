import { Department } from "./departement.models";
import { GradeDuration } from "./gradeDuration.models";
import { Semestre } from "./semestre.models";

export interface Major {
    id:          number;
    name:String;
    label:     string;
    nameAndDepartment:string;
    semesters:   Semestre[];
    department: Department;
    gradeDurations:GradeDuration[]
}
