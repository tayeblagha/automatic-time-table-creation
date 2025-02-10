import { Classes } from "./classes.models";
import { Department } from "./departement.models";
import { Major } from "./majors.models";
import { Prof } from "./prof.models";
import { Room } from "./rooms.models";

export interface PageProf {
    content:          Prof[];
    totalPages:       number;
    totalElements:    number;
    size:             number;
    number:           number;
    numberOfElements: number;
    
}
export interface PageStudent {
    content:          Prof[];
    totalPages:       number;
    totalElements:    number;
    size:             number;
    number:           number;
    numberOfElements: number;
    
}
export interface PageDepartment {
    content:          Department[];
    totalPages:       number;
    totalElements:    number;
    size:             number;
    number:           number;
    numberOfElements: number;
    
}
export interface PageRoom {
    content:          Room[];
    totalPages:       number;
    totalElements:    number;
    size:             number;
    number:           number;
    numberOfElements: number;
    }
export interface PageMajor {
    content:          Major[];
    totalPages:       number;
    totalElements:    number;
    size:             number;
    number:           number;
    numberOfElements: number;
    }
export interface PageClasses {
    content:          Classes[];
    totalPages:       number;
    totalElements:    number;
    size:             number;
    number:           number;
    numberOfElements: number;
    }
    