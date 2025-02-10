import { Classes } from "./classes.models";

export interface Student {
    id?:         number;
    lastName:   string;
    firstName:  string;
    email:      string;
    login:      string;
    password:   string;
    classes?:Partial<Classes>
  

}