import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Major } from '../models/majors.models';
import { PageMajor } from '../models/profPage.models';
import {ConsoleLogger} from "@angular/compiler-cli";
import { GradeDuration } from '../models/gradeDuration.models';

@Injectable({
  providedIn: 'root'
})
export class MajorService {
  
   constructor(private http:HttpClient) { }
  public getAllMajors(): Observable<Major[]> {
    return this.http.get<Major[]>(`${environment.backendHost}/majors/all`);
  }

  public findByDepartmentsIds(departmentIds: number[]): Observable<Major[]> {
    const url = `${environment.backendHost}/majors/departmentids`;
    return this.http.post<Major[]>(url, departmentIds);
  }
  
   public getMajors(page: number, size: number): Observable<PageMajor> {
    return this.http.get<PageMajor>(environment.backendHost + "/majors?page=" + page + "&size=" + size);
  }
  public searchMajors(keyword : string,page: number, size: number):Observable<PageMajor>{
    return this.http.get<PageMajor>(environment.backendHost+"/majors/search?keyword="+keyword+"&page=" + page + "&size=" + size)
  }
  public saveMajor(Major: Major):Observable<Major>{
    return this.http.post<Major>(environment.backendHost+"/majors",Major);
  }
  public updateMajor(id: number,Major: Partial<Major>):Observable<Major>{
    return this.http.put<Major>(environment.backendHost+"/majors/"+id,Major);
  }
  public getMajor(id: number):Observable<Major>{
    return this.http.get<Major>(environment.backendHost+"/majors/"+id);
  }
  public deleteMajor(id: number): Observable<any>{
    return this.http.delete(environment.backendHost+"/majors/"+id);
  }
  public getSemsterByMajor(id: number):Observable<any>{
    return this.http.get(environment.backendHost+"/majors/"+id+"/semesters");
  }
  public updateMajorGradeDuration(id:any, gradeDurations:GradeDuration[]):Observable<Major>{
    return this.http.put<Major>(environment.backendHost+"/majors/"+id+"/grade_duration",gradeDurations)
}

 // New methods for the added endpoints
 public findByGradeDurationGrade(major_id: any,department_id:any): Observable<Major[]> {
  return this.http.get<Major[]>(`${environment.backendHost}/majors/grade/${major_id}`+"/department/"+department_id);
}

public findByGradeDurationGradeAndSemesterNumber(id: number, semester: string): Observable<Major[]> {
  return this.http.get<Major[]>(`${environment.backendHost}"/majors/grade/${id}/${semester}`);
}


public findByDepartment(department_id: any): Observable<Major[]> {
  return this.http.get<Major[]>(`${environment.backendHost}`+     "/majors/department/" +department_id  );
}

public findByDepartments(departmentIds: number[], page: number, size: number): Observable<PageMajor> {
  const url = `${environment.backendHost}/majors/departments?page=${page}&size=${size}`;
  return this.http.post<PageMajor>(url, departmentIds);
}

public findByLabel(departmentIds: number[], page: number, size: number,word:any): Observable<PageMajor> {
  const url = `${environment.backendHost}/majors/departments/${word}?page=${page}&size=${size}`;
  return this.http.post<PageMajor>(url, departmentIds);
}



}



