import { Classes } from "./classes.models";
import { Semestre } from "./semestre.models";

export interface Module {
    id:        number;
    label:   string;
    classRate: number;
    isSeperated : boolean;
    isMetuale: boolean;
    classes:Classes;
}