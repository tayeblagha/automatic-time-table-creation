import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PageClasses } from '../models/profPage.models';
import { Classes } from '../models/classes.models';

@Injectable({
  providedIn: 'root'
})
export class ClassesService {

   constructor(private http:HttpClient) { }
   public searchClassesByLabelAndDepartment(keyword : string,departement_id:any, page: number, size: number):Observable<PageClasses>{
    return this.http.get<PageClasses>(environment.backendHost+"/classes/search/" +departement_id+"?keyword="+keyword+"&page=" + page + "&size=" + size)
  }
   public getClasses(page: number, size: number): Observable<PageClasses> {
    return this.http.get<PageClasses>(environment.backendHost + "/classes?page=" + page + "&size=" + size);
  }
  public searchClassesSem(keyword : string, sem:number,page: number, size: number):Observable<PageClasses>{
    return this.http.get<PageClasses>(environment.backendHost+"/classes/searchSem?keyword="+keyword+"&page=" + page + "&size=" + size+"&sem="+sem)
  }
  public searchClasses(keyword : string, page: number, size: number):Observable<PageClasses>{
    return this.http.get<PageClasses>(environment.backendHost+"/classes/search?keyword="+keyword+"&page=" + page + "&size=" + size)
  }
  public saveClasse(Classe: Classes,id:any):Observable<Classes>{
    return this.http.post<Classes>(environment.backendHost+"/classes/"+id,Classe);
  }
  public updateClasse(id: number,Classe: Classes):Observable<Classes>{
    return this.http.put<Classes>(`${environment.backendHost}/classes/${id}`,Classe);
  }
  public getClasse(id: number):Observable<Classes>{
    return this.http.get<Classes>(environment.backendHost+"/classes/"+id);
  }
  public deleteClasse(id: number): Observable<any>{
    return this.http.delete(environment.backendHost+"/classes/"+id);
  }
  
	public getClassesByDepartmentandGrade(departmentId:any,grade:any):Observable<Classes[]>{
		  return this.http.get<Classes[]>(environment.backendHost+"/classes/department/"+departmentId+ "/grade/"+grade);
	}

 
  
}
