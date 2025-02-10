import { Department } from "./departement.models";
import { Major } from "./majors.models";
import { Module } from "./modules.models";
import { Semestre } from "./semestre.models";

export interface GradeDuration {
    grade:number,
    durationPerWeek:number,
}