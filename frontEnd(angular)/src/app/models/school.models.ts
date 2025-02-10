import { Semestre } from "./semestre.models";

export interface SchoolGeneralInfo {
    id: number;
    name: string;
    imageUrl:string
    academicYear: {
        id: number;
        startYear: number;
        endYear: number;
    };
    numberSemesters: Number;
}
