import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from "@angular/forms";
import { Classes } from "../../../models/classes.models";
import { Router } from "@angular/router";
import Swal from "sweetalert2";
import { ClassesService } from "../../../services/classe.service";
import { Department } from 'src/app/models/departement.models';
import { DepartmentService } from 'src/app/services/department.service';
import { Observable } from 'rxjs';
import { StudentService } from 'src/app/services/student.service ';

@Component({
  selector: 'app-managment-classe',
  templateUrl: './managment-classes.component.html',
  styleUrls: ['./managment-classes.component.css']
})
export class ManagmentClassesComponent implements OnInit {
  departmentId: any = -1; // Initialize with no department selected
  classes: Classes[] = [];
  selectedDepartments: Department[] = [];
  departments: Department[] = [];
  errorMessage!: string;
  searchFormGroup!: FormGroup;
  page: number = 0;
  size: number = 6;
  totalPages: number = 0;
  currentPage: number = 0;
  totalelements: number = 0;

  displayedPages: number[] = [];
  dropdownSettings = {
    singleSelection: true,
    idField: 'id',
    textField: 'name',
    selectAllText: 'Select All',
    unSelectAllText: 'Unselect All',
    itemsShowLimit: 5,
    allowSearchFilter: true,
  };

  

  constructor(
    private classeService: ClassesService,
    private fb: FormBuilder,
    private router: Router,
    private departmentService: DepartmentService,
    private studentService:StudentService
  ) { }

  ngOnInit(): void {
    this.searchFormGroup = this.fb.group({
      keyword: this.fb.control('')
    });
    this.handleSearchClasses();
    this.getAllDepartments();
  }

  onDepartmentSelect(item: any): void {
    this.departmentId = item.id;
    this.page = 0; // Reset page
    this.currentPage = 0; // Reset current page
    this.searchClassesByLabelAndDepartment();
  }

  onDepartmentDeselect(item: any): void {
    this.departmentId = -1; // Reset to no department
    this.page = 0; // Reset page
    this.currentPage = 0; // Reset current page
    this.handleSearchClasses();
  }

  handleEditeClasse(classeEdit: Classes): void {
    this.router.navigateByUrl('/classes/edit/' + classeEdit.id, { state: classeEdit });
  }

  handleChangeSize($event: Event): void {
    this.size = parseInt((<HTMLInputElement>$event.target).value);
    this.page = 0; // Reset page
    this.currentPage = 0; // Reset current page
    this.handleSearchClasses();
  }

  

  handleDeleteClasse(classe: Classes): void {
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
        this.classeService.deleteClasse(classe.id).subscribe();
        this.classes = this.classes.filter((c) => c.id !== classe.id);
      }
    });
  }

  setDisplayedPages(): void {
    this.displayedPages = [];
    const startPage = Math.floor(this.currentPage / 5) * 5;
    for (let i = startPage; i < startPage + 5 && i < this.totalPages; i++) {
      this.displayedPages.push(i);
    }
  }

  handleLabel(): void {
    this.page = 0; // Reset page
    this.currentPage = 0;
    if (this.departmentId === -1) {
      this.handleSearchClasses();
    } else {
      this.searchClassesByLabelAndDepartment();
    }
  }

  handleFunctions(): void {
  
    if (this.departmentId === -1) {
      this.handleSearchClasses();
    } else {
      this.searchClassesByLabelAndDepartment();
    }
  }

  gotoPage(page: number): void {
    this.page = page;
    this.currentPage = page;
    this.handleFunctions();
  }

  goToPreviousSet(): void {
    const startPage = Math.floor(this.currentPage / 5) * 5;
    if (startPage - 5 >= 0) {
      this.page = startPage - 5;
      this.currentPage = this.page;
      this.handleFunctions();
    }
  }

  goToNextSet(): void {
    const startPage = Math.floor(this.currentPage / 5) * 5;
    if (startPage + 5 < this.totalPages) {
      this.page = startPage + 5;
      this.currentPage = this.page;
      this.handleFunctions();
    }
  }

  getAllDepartments(): void {
    this.departmentService.getDepartements().subscribe(
      (data) => {
        this.departments = data;
      },
      (error) => {
        console.log(error);
      }
    );
  }

  handleSearchClasses(): void {
    
    this.classeService
      .searchClasses(this.searchFormGroup.value.keyword, this.page, this.size)
      .subscribe(
        (data) => {
          this.classes = data.content;
          this.totalPages = data.totalPages;
          this.currentPage = data.number;
          this.setDisplayedPages();
        },
        (error) => {
          this.errorMessage = error;
          console.log(error);
        }
      );
  }

  searchClassesByLabelAndDepartment(): void {
    const keyword = this.searchFormGroup.value.keyword;
    this.classeService
      .searchClassesByLabelAndDepartment(keyword, this.departmentId, this.page, this.size)
      .subscribe(
        (data) => {
          this.classes = data.content;
          this.totalPages = data.totalPages;
          this.currentPage = data.number;
          this.setDisplayedPages();
        },
        (error) => {
          this.errorMessage = error;
          console.log(error);
        }
      );
  }


 
  
}
