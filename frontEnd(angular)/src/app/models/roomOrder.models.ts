import { Classes } from "./classes.models";
import { Room } from "./rooms.models";


export interface RoomOrder {
    id:        number;
    orderIndex:  number;
    classroom: Room;
    classes:Classes;
}
