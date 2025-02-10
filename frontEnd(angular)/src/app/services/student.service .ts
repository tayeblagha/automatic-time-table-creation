import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Student } from '../models/student.models';
import { environment } from 'src/environments/environment';
import { PageStudent } from '../models/profPage.models';

@Injectable({
  providedIn: 'root',
})
export class StudentService {
  private readonly url = `${environment.backendHost}/students`;

  constructor(private http: HttpClient) {}

  // Create a new student
  createStudent(student: Student,class_id:any): Observable<Student> {
    return this.http.post<Student>(this.url+"/"+class_id, student);
  }

  // Get a student by ID
  getStudentById(id: number): Observable<Student> {
    return this.http.get<Student>(`${this.url}/${id}`);
  }

  // Update a student
  updateStudent(id: number, student: Student): Observable<Student> {
    return this.http.put<Student>(`${this.url}/${id}`, student);
  }

  // Delete a student
  deleteStudent(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }

  // Get all students
  getAllStudents(): Observable<Student[]> {
    return this.http.get<Student[]>(this.url);
  }

  // Search students by keyword
  searchStudents(keyword: string, page: number, size: number): Observable<PageStudent> {
    const params = new HttpParams()
      .set('keyword', keyword)
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get<PageStudent>(`${this.url}/search`, { params });
  }

  // Find students by class ID
  findStudentsByClassId(classId: number): Observable<Student[]> {
    return this.http.get<Student[]>(`${this.url}/class/${classId}`);
  }

  // Search students by class and keyword
  findStudentsByClassAndKeyword(
    classId: number,
    keyword: string,
    page: number,
    size: number
  ): Observable<PageStudent> {
    const params = new HttpParams()
      .set('keyword', keyword)
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get<PageStudent>(`${this.url}/class/${classId}/search`, { params });
  }

  public countStudent(classId: number):Observable<number> {
    return this.http.get<number>(this.url+"/class/count/" +classId  );
  }


}
