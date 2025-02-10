import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from "../../environments/environment";
import { PageDepartment } from '../models/profPage.models';
import { Department } from '../models/departement.models';
import { Major } from '../models/majors.models';

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {

  constructor(private http: HttpClient) { }

  public searchDepartments(keyword: string, page: number, size: number): Observable<PageDepartment> {
    return this.http.get<PageDepartment>(`${environment.backendHost}/departments/search?keyword=${keyword}&page=${page}&size=${size}`);
  }

  public saveDepartment(department: Department): Observable<Department> {
    return this.http.post<Department>(`${environment.backendHost}/departments`, department);
  }

  public updateDepartment(id: number, department: Department): Observable<Department> {
    return this.http.put<Department>(`${environment.backendHost}/departments/${id}`, department);
  }

  public getDepartment(id: number): Observable<Department> {
    return this.http.get<Department>(`${environment.backendHost}/departments/${id}`);
  }
  public getDepartements(): Observable<Department[]> {
    return this.http.get<Department[]>(`${environment.backendHost}/departments`);
  }

  public deleteDepartment(id: number): Observable<any> {
    return this.http.delete(`${environment.backendHost}/departments/${id}`);
  }

  public getMajors(id: number): Observable<Major[]> {
    return this.http.get<Major[]>(`${environment.backendHost}/departments/${id}/majors`);
  }
}
