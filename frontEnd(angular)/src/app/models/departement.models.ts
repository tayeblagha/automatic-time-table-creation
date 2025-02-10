import { Classes } from "./classes.models";
import { Major } from "./majors.models";

export interface Department {
    id:      number;
    name:string
    label: string;
    classes: Classes[];
}
