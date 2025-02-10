import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Department } from 'src/app/models/departement.models';
import { Prof } from 'src/app/models/prof.models';
import { DepartmentService } from 'src/app/services/department.service';
import { ProfServiceService } from 'src/app/services/prof-service.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-managment-prof',
  templateUrl: './managment-prof.component.html',
  styleUrls: ['./managment-prof.component.css']
})
export class ManagmentTeacherComponent implements OnInit {

  departments: Department[] = [];
  temp_label = "";
  profs!: Prof[];
  errorMessage!: string;
  searchFormGroup!: FormGroup;
  page: number = 0;
  size: number = 6;
  totalPages: number = 0;
  currentPage: number = 0;
  totalelements: number = 0;
  displayedPages: number[] = [];
  selectedDepartment: Department | null = null;

  dropdownSettings = {
    singleSelection: true, 
    idField: 'id',
    textField: 'name',
    selectAllText: 'Select All',
    unSelectAllText: 'Unselect All',
    itemsShowLimit: 3,
    allowSearchFilter: true,
  };

  option1: number = 0;
  option2: number = 0;
  option3: number = 0;
  option4: number = 0;

  constructor(
    private profService: ProfServiceService,
    private fb: FormBuilder,
    private router: Router,
    private departmentService: DepartmentService
  ) {}

  ngOnInit(): void {
    this.getAllDepartments();
    this.searchFormGroup = this.fb.group({
      keyword: this.fb.control('')
    });
    this.handleSearchCustomers();
  }

  handleEditeProf(profedit: Prof) {
    this.router.navigateByUrl('/teachers/edit/' + profedit.id, { state: profedit });
  }

  handleChangeSize(event: Event) {
    this.size = parseInt((<HTMLInputElement>event.target).value);
    this.handleSearchCustomers();
  }

  handleSearchCustomers() {
    const kw = this.searchFormGroup?.value.keyword;
    this.profService.searchProfs(kw, this.page, this.size).subscribe({
      next: (data) => {
        this.profs = data.content;
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

  handleDeleteProf(prof: Prof) {
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
        this.profService.deleteProf(prof.id).subscribe(() => {
          this.profs.splice(this.profs.indexOf(prof), 1);
        });
      }
    });
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

  onDepartmentSelect(event:any): void {
   
    this.searchProfsbyLabelAndDepartment(event.id);
  }

  onDepartmentDeselect(): void {
    this.selectedDepartment = null;
    this.handleSearchCustomers();
    this.currentPage = 0;
    this.page = 0;
  }

  getAllDepartments() {
    this.departmentService.getDepartements().subscribe(
      data => {
        this.departments = data;
      },
      error => {
        console.error('Error fetching departments:', error);
      }
    );
  }

  searchProfsbyLabelAndDepartment(id:any) {
   
    this.currentPage = 0;
    this.page = 0;
    this.profService.searchProfsbyLabelAndDepartment(
      id,
      this.temp_label,
      this.page,
      this.size
    ).subscribe({
      next: (data) => {
        this.profs = data.content;
        this.totalPages = data.totalPages;
        this.currentPage = data.number;
        this.totalelements = data.totalElements;
        this.setDisplayedPages();
      },
      error: (err) => {
        console.error('Error searching profs:', err);
      }
    });
  }

  calculateOptions() {
    this.option1 = Math.ceil(this.totalelements / 4);
    this.option2 = Math.ceil(this.totalelements / 2);
    this.option3 = Math.ceil((this.totalelements / 4) * 3);
    this.option4 = this.totalelements;
  }


  handleTeacherInfo(prof:Prof){
    this.router.navigateByUrl('/teachers/details/' + prof.id, { state: prof });
   
  }
}
