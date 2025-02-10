import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Prof } from 'src/app/models/prof.models';
import { Student } from 'src/app/models/student.models';
import { DepartmentService } from 'src/app/services/department.service';
import { ProfServiceService } from 'src/app/services/prof-service.service';
import { StudentService } from 'src/app/services/student.service ';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-managment-student',
  templateUrl: './managment-student.component.html',
  styleUrls: ['./managment-student.component.css']
})
export class ManagmentStudentComponent {


  temp_label = "";
  students!: Student[];
  errorMessage!: string;
  searchFormGroup!: FormGroup;
  page: number = 0;
  size: number = 6;
  totalPages: number = 0;
  currentPage: number = 0;
  totalelements: number = 0;
  displayedPages: number[] = [];



  option1: number = 0;
  option2: number = 0;
  option3: number = 0;
  option4: number = 0;

  constructor(
    private studentService: StudentService,
    private fb: FormBuilder,
    private router: Router,
    private departmentService: DepartmentService
  ) {}

  ngOnInit(): void {
    this.searchFormGroup = this.fb.group({
      keyword: this.fb.control('')
    });
    this.handleSearchCustomers();
  }

  handleEditeStudent(student: Student) {
    this.router.navigateByUrl('/students/edit/' + student.id, { state: student });
  }

  handleChangeSize(event: Event) {
    this.size = parseInt((<HTMLInputElement>event.target).value);
    this.handleSearchCustomers();
  }

  handleSearchCustomers() {
    const kw = this.searchFormGroup?.value.keyword;
    this.studentService.searchStudents(kw, this.page, this.size).subscribe({
      next: (data) => {
        this.students = data.content;
        this.totalPages = data.totalPages;
        this.currentPage = data.number;
        this.totalelements = data.totalElements;
        this.setDisplayedPages();
        this.calculateOptions();
      },
      error: (err) => {
        this.errorMessage = err;
        console.log(err);
      }
    });
  }

  handleDeleteStudent(student: Student) {
    Swal.fire({
      title: 'Are you sure?',
      text: "You won't be able to go back!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
      if (result.isConfirmed) {
        this.studentService.deleteStudent(Number(student.id)).subscribe(() => {
          this.students.splice(this.students.indexOf(student), 1);
        });
      }
    });
  }
  handleStudentInfo(student:Student){
    this.router.navigateByUrl('/students/details/' + student.id, { state: student });
  }

  setDisplayedPages() {
    this.displayedPages = [];
    const startPage = Math.floor(this.currentPage / 5) * 5;
    for (let i = startPage; i < startPage + 5 && i < this.totalPages; i++) {
      this.displayedPages.push(i);
    }
  }

  gotoPage(page: number) {
    this.currentPage = page;
    this.page = page;
    this.handleSearchCustomers();
  }

  goToPreviousSet() {
    const startPage = Math.floor(this.currentPage / 5) * 5;
    if (startPage - 5 >= 0) {
      this.currentPage = startPage - 5;
      this.page = this.currentPage;
      this.handleSearchCustomers();
    }
  }

  goToNextSet() {
    const startPage = Math.floor(this.currentPage / 5) * 5;
    if (startPage + 5 < this.totalPages) {
      this.currentPage = startPage + 5;
      this.page = this.currentPage;
      this.handleSearchCustomers();
    }
  }

  searchByLabel() {
    this.handleSearchCustomers();
    this.currentPage = 0;
    this.page = 0;
  }





 

  calculateOptions() {
    this.option1 = Math.ceil(this.totalelements / 4);
    this.option2 = Math.ceil(this.totalelements / 2);
    this.option3 = Math.ceil((this.totalelements / 4) * 3);
    this.option4 = this.totalelements;
  }
}
