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
export class ProfServiceService {

   constructor(private http:HttpClient) { }

   public getProfs(page: number, size: number): Observable<PageProf> {
    return this.http.get<PageProf>(environment.backendHost + "/teachers?page=" + page + "&size=" + size);
  }
  
  public searchProfsbyLabelAndDepartment(department_id:any,keyword : string,page: number, size: number):Observable<PageProf>{
    return this.http.get<PageProf>(environment.backendHost+"/teachers/search/department/"+  department_id+ "?keyword="+keyword+"&page=" + page + "&size=" + size)
  }

  public searchProfs(keyword : string,page: number, size: number):Observable<PageProf>{
    return this.http.get<PageProf>(environment.backendHost+"/teachers/search?keyword="+keyword+"&page=" + page + "&size=" + size)
  }
  public saveProf(Prof: Prof):Observable<Prof>{
    return this.http.post<Prof>(environment.backendHost+"/teachers",Prof);
  }
  public updateProf(id: any,Prof: Prof):Observable<Prof>{
    return this.http.put<Prof>(environment.backendHost+"/teachers/"+id,Prof);
  }
  public getProf(id: number):Observable<Prof>{
    return this.http.get<Prof>(environment.backendHost+"/teachers/"+id);
  }
  public deleteProf(id: any): Observable<any>{
    return this.http.delete(environment.backendHost+"/teachers/"+id);
  }

  public getTCM(class_id: number):Observable<TeacherClasseMajor>{
    return this.http.get<TeacherClasseMajor>(environment.backendHost+"/teachers/tcm/class/"+class_id);
  }

  public getHashTCM(class_id: number): Observable<TeacherHashMap> {
    return this.http.get<TeacherHashMap >(`${environment.backendHost}/teachers/tcm/hashclass/${class_id}`);
  }




  public getTeachersByMajor(major_id: number): Observable<Prof[]> {
    return this.http.get<Prof []>(`${environment.backendHost}/teachers/major/` +major_id);
  }

  
  

 

}
