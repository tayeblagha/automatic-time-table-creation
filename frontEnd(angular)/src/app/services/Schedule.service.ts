import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ModuleElement } from '../models/moduleElement.models';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { RawData } from 'src/app/models/rawData.models';
import { ScheduleSlot, Timetable } from '../models/timetable.models';

@Injectable({
  providedIn: 'root'
})
export class ScheduleService {
 
  link=environment.backendHost+"/timetable"

  constructor(private http:HttpClient) { }
  public searchslot(classId: number,grade:number): Observable<ScheduleSlot> {
    return this.http.get<ScheduleSlot>(`${this.link}/searchslot/${classId}/grade/${grade}`);
  }


  public getCurrentTimetableById(classId: number): Observable<Timetable[]> {
    return this.http.get<Timetable[]>(`${this.link}/current/${classId}`);
  }

  public generateSchedule(thedate:string):Observable<Timetable[]>{
    return this.http.get<Timetable[]>(this.link+"/generate/" + thedate  )
  }
  public generate(grade_id:any,department_id:any):Observable<RawData[]>{
    return this.http.get<RawData[]>(this.link+"/"+grade_id+"/"+department_id)
  }

  public getElements(): Observable<ModuleElement[]> {
    return this.http.get<ModuleElement[]>(environment.backendHost + "/moduleElements");
  }
  getProfSchedule(idProf: number) {
    return this.http.get<ModuleElement[]>(environment.backendHost + "/schedules/teacher/" + idProf);
  }
   getClassSchedule(classeId: number) {
    return this.http.get<ModuleElement[]>(environment.backendHost + "/schedules/" + classeId);
  }


  public getTimetablesByStartDate(start_date: string): Observable<Timetable[]> {
    return this.http.get<Timetable[]>(`${this.link}/search/${start_date}`);
  }


}
