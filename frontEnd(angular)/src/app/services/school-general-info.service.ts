import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { SchoolGeneralInfo } from '../models/school.models';

@Injectable({
  providedIn: 'root'
})
export class SchoolGeneralInfoService {
  private apiUrl = environment.backendHost + "/school/";

  constructor(private httpClient: HttpClient) { }

  // Get school general info
  getSchoolGeneralInfo(): Observable<SchoolGeneralInfo> {
    return this.httpClient.get<SchoolGeneralInfo>(this.apiUrl);
  }

  // Update school general info
  updateSchoolGeneralInfo(updatedInfo: SchoolGeneralInfo): Observable<SchoolGeneralInfo> {
    return this.httpClient.put<SchoolGeneralInfo>(this.apiUrl, updatedInfo);
  }
}
