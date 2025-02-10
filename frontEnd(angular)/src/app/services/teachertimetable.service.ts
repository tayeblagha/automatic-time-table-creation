import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { TeachersTimeTable } from "../models/teachertimetables.models";
import { Injectable } from "@angular/core";
import { DayTimeDto } from "../models/daytimedto.models";
@Injectable({
  providedIn: 'root'
})
export class TeacherTimeTableService {

   private apiUrl = environment.backendHost + "/teacherstimtable";
  
    constructor(private httpClient: HttpClient) { }

    createTimeTable(teachersTimeTable: TeachersTimeTable): Observable<TeachersTimeTable> {
        return this.httpClient.post<TeachersTimeTable>(`${this.apiUrl}`, teachersTimeTable);
    }

    getTimeTable(teacherId: number, startDate: string): Observable<TeachersTimeTable> {
        return this.httpClient.get<TeachersTimeTable>(`${this.apiUrl}/${teacherId}/${startDate}`);
    }

    getAllTimeTables(): Observable<TeachersTimeTable[]> {
        return this.httpClient.get<TeachersTimeTable[]>(this.apiUrl);
    }

    deleteTimeTable(teacherId: number, startDate: string): Observable<void> {
        return this.httpClient.delete<void>(`${this.apiUrl}/${teacherId}/${startDate}`);
    }

    getCurrentTimeTable(teacherId: number): Observable<TeachersTimeTable> {
        return this.httpClient.get<TeachersTimeTable>(`${this.apiUrl}/current/${teacherId}`);
    }

    getCurrentSlot(teacherId: number): Observable<DayTimeDto> {
        return this.httpClient.get<DayTimeDto>(`${this.apiUrl}/currentslot/${teacherId}`);
    }



    
}