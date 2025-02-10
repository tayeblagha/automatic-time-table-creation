import { Classes } from "./classes.models";
import { Department } from "./departement.models";
import { Major } from "./majors.models";
import { Module } from "./modules.models";
import { Prof } from "./prof.models";
import { Semestre } from "./semestre.models";

export interface TeacherClasseMajor {
    classes:Classes,
    major:Partial<Major>,
    teacher:Partial<Prof>,

}