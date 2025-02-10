import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { NotAvailable } from '../models/notAvailable.models';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class NotAvaliableService {
  constructor(private http:HttpClient) { }

  public getAllNotAvaliables():Observable<NotAvailable[]> {
    return this.http.get<NotAvailable[]>(`${environment.backendHost}/notAvailables`);
  }

  public getNotAvaliableById(id:number):Observable<NotAvailable>{
    return this.http.get<NotAvailable>(`${environment.backendHost}/notAvailables/${id}`)
  }

  public getNotAvaliableByTeacherId(id:number):Observable<NotAvailable[]>{
    return this.http.get<NotAvailable[]>(`${environment.backendHost}/notAvailables/teacher/${id}`)
  }

  public createNotAvaliable(notAvailable:NotAvailable):Observable<NotAvailable>{
    return this.http.post<NotAvailable>(`${environment.backendHost}/notAvailables`, notAvailable);
  }

  public updateNotAvaliablr(id:number, notAvailable:NotAvailable):Observable<NotAvailable>{
    return this.http.put<NotAvailable>(`${environment.backendHost}/notAvailables/${id}`, notAvailable);
  }

  public deleteNotAvaliable(id: number): Observable<any> {
    return this.http.delete(`${environment.backendHost}/notAvailables/${id}`);
  }

}
