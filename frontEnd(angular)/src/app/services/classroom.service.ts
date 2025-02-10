import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Room } from '../models/rooms.models';
import { PageRoom } from '../models/profPage.models';

@Injectable({
  providedIn: 'root'
})
export class RoomService {
  constructor(private http: HttpClient) { }

  public getRooms(page: number, size: number): Observable<PageRoom> {
    return this.http.get<PageRoom>(environment.backendHost + "/classrooms?page=" + page + "&size=" + size);
  }

  public getAllRooms(): Observable<Room[]> {
    return this.http.get<Room[]>(environment.backendHost + "/classrooms/all");
  }

  public searchRooms(keyword: string,typeroom:any, page: number, size: number): Observable<PageRoom> {
    return this.http.get<PageRoom>(environment.backendHost + "/classrooms/search?keyword=" + keyword +"&typeroom=" + typeroom+ "&page=" + page + "&size=" + size);
  }

  public saveRoom(salle: Partial<Room>): Observable<Room> {
    return this.http.post<Room>(environment.backendHost + "/classrooms", salle);
  }

  public updateRoom(id: number, salle: Room): Observable<Room> {
    return this.http.put<Room>(environment.backendHost + "/classrooms/" + id, salle);
  }

  public getRoom(id: number): Observable<Room> {
    return this.http.get<Room>(environment.backendHost + "/classrooms/" + id);
  }

   public deleteRoom(id: number): Observable<any> {
    return this.http.delete(`${environment.backendHost}/classrooms/${id}`);
  }
}
