import { Module } from "./modules.models";
import { Prof } from "./prof.models";
import { Room } from "./rooms.models";

export interface ModuleElement {
    id:         number;
    label:      string;
    module:     Module ;
    teacher:    Prof;
    day:        string;
    period:     string;
    classroom:       Room;
}