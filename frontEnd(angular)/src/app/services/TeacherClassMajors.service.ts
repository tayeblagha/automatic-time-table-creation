import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Prof, TeacherHashMap } from '../models/prof.models';
import {environment} from "../../environments/environment";
import { PageProf } from '../models/profPage.models';
import { TeacherClasseMajor } from '../models/teacherClasseMajor.models ';

@Injectable({
  providedIn: 'root'
})
export class TeacherClassMajorsService{

     url = environment.backendHost + "/teachers/tcm";

     constructor(private http:HttpClient) { }

     // Get all
     findAll(): Observable<TeacherClasseMajor[]> {
         return this.http.get<TeacherClasseMajor[]>(this.url);
     }

     // Get by ID
     findById(classesId: any, majorId: any): Observable<TeacherClasseMajor> {
         return this.http.get<TeacherClasseMajor>(`${this.url}/${classesId}/${majorId}`);
     }

     // Create
     create(teacherClassMajor: Partial<TeacherClasseMajor>): Observable<TeacherClasseMajor> {
         return this.http.post<TeacherClasseMajor>(this.url, teacherClassMajor);
     }

     // Update
     update(teacherClassMajor: Partial<TeacherClasseMajor>, majorid: any, classid: any): Observable<TeacherClasseMajor> {
         const params = { majorid: majorid.toString(), classid: classid.toString() };
         return this.http.put<TeacherClasseMajor>(this.url, teacherClassMajor, { params });
     }

     // Delete
     deleteById(classesId: number, majorId: number): Observable<void> {
         return this.http.delete<void>(`${this.url}/${classesId}/${majorId}`);
     }
}
