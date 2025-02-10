import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { RoomOrder } from '../models/roomOrder.models'; // Assuming you have defined this model

@Injectable({
  providedIn: 'root'
})
export class RoomOrderService {
  
  private url = environment.backendHost + "/roomorders/";

  constructor(private http: HttpClient) { }

  // Method to get room orders by class ID
  getRoomOrdersByClassId(classId: number): Observable<RoomOrder[]> {
    return this.http.get<RoomOrder[]>(`${this.url}class/${classId}`);
  }

  // Method to update room orders
  updateRoomOrders(roomOrders: RoomOrder[]): Observable<RoomOrder[]> {
    return this.http.put<RoomOrder[]>(this.url, roomOrders);
  }
}
