import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Semestre } from '../models/semestre.models';

@Injectable({
  providedIn: 'root'
})
export class SemesterService {

   private apiUrl = environment.backendHost + "/semesters/";
  
    constructor(private httpClient: HttpClient) { }

    getFirstNSemesters(N:Number):Observable<Semestre[]>{
      return this.httpClient.get<Semestre[]>(this.apiUrl+N)
    }
    updateSemester(id:any,semester:Semestre):Observable<Semestre>{
      return this.httpClient.put<Semestre>(this.apiUrl+id,semester)

    }
    updateCurrentYear(year:any){
      return this.httpClient.get(this.apiUrl)
    }

  
}
