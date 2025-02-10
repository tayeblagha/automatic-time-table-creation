import { Department } from "./departement.models";
import { Major } from "./majors.models";
import { Module } from "./modules.models";
import { Semestre } from "./semestre.models";

export interface Classes {
    id:        number;
    name:String;
    label:   string;
    grade:Number
    numberOfStudents: number;
    majors:   Major[] ;
    department:Department;
}