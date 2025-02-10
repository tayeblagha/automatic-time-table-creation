import {Component, OnInit} from '@angular/core';
import {Department} from "../../../models/departement.models";
import {FormBuilder, FormGroup} from "@angular/forms";
import {DepartmentService} from "../../../services/department.service";
import {Router} from "@angular/router";
import Swal from "sweetalert2";
import {Major} from "../../../models/majors.models";
import {MajorService} from "../../../services/major.service";
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Component({
  selector: 'app-managment-major',
  templateUrl: './managment-major.component.html',
  styleUrls: ['./managment-major.component.css']
})
export class ManagmentMajorComponenet implements OnInit{
  temp_label:String=""

  majors: Major [] = [];
  majorsIds:number[]=[];
  departments:Department[]=[]
  errorMessage!: string;
  searchFormGroup!: FormGroup;
  page: number = 0;
  size: number = 6;
  totalPages: number = 0;
  currentPage: number = 0;
  totalelements:number=0;
  displayedPages: number[] = [];

  selectedDepartments: Department[] = []; // Stores the selected departments
departmentsIds: number[] = []; // Stores the IDs of selected departments

dropdownSettings = {
  singleSelection: true, // Allow multiple selection
  idField: 'id',
  textField: 'name',
  selectAllText: 'Select All',
  unSelectAllText: 'Unselect All',
  itemsShowLimit: 3,
  allowSearchFilter: true, // Enable search functionality
};

  constructor(
    private majorService: MajorService,
    private fb: FormBuilder,
    private router: Router,
    private departmentService:DepartmentService,
    private sanitizer: DomSanitizer
  ) {
 
  }

  ngOnInit(): void {
    this.searchFormGroup = this.fb.group({
      keyword: this.fb.control('')
    });
    this.handleSearchMajors();
    this.getAllDepartments();
  }

  onDepartmentSelect(item: any): void {
    this.departmentsIds=[]
    this.departmentsIds.push(item.id);
    this.handleSearchMajorsbyDepartments()
   

  }
  
  onDepartmentDeselect(item:any): void {
    this.departmentsIds=[]
    this.searchByLabel()
    
    
  }
  
  
  handleEditMajor(majorEdit: Major) {
    this.router.navigateByUrl('/majors/edit/'+majorEdit.id,{state :majorEdit});
  }
  handleChangeSize($event: Event) {
    this.size = parseInt((<HTMLInputElement>$event.target).value);
    this.handleSearchMajors();
  }
  handleSearchMajors(): void {
    this.majorService
      .searchMajors(this.searchFormGroup.value.keyword, this.page, this.size)
      .subscribe(
        (data) => {
          this.majors = data.content;
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

  handleSearchMajorsbyDepartments(): void {
    this.currentPage = 0;
    this.page = 0;
    if (this.temp_label==""){
      this.majorService
      .findByDepartments(this.departmentsIds,this.page,this.size)
        .subscribe(
          (data) => {
            this.majors = data.content;
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
    else{
      this.searchByLabel()
    }
   
   
  }

  handleDeleteMajor(major: Major): void {
    Swal.fire({
      title: 'Are you sure',
      text: "You won't be able to go back !",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it !'
    }).then((result) => {
      if (result.isConfirmed) {
        this.majorService.deleteMajor(major.id).subscribe();
        this.majors = this.majors.filter((f) => f.id !== major.id);

      }
    });
  }

  setDisplayedPages(): void {
    this.displayedPages = [];
    const startPage = Math.floor(this.currentPage / 5) * 5; // Change 3 to 5
    for (
      let i = startPage;
      i < startPage + 5 && i < this.totalPages; // Change 3 to 5
      i++
    ) {
      this.displayedPages.push(i);
    }
  }
  
  goToPreviousSet(): void {
    const startPage = Math.floor(this.currentPage / 5) * 5; // Change 3 to 5
    if (startPage - 5 >= 0) { // Change 3 to 5
      this.currentPage = startPage - 5; // Change 3 to 5
      this.page = this.currentPage;
      this.handleSrollFunction();
    }
  }
  
  goToNextSet(): void {
    const startPage = Math.floor(this.currentPage / 5) * 5; // Change 3 to 5
    if (startPage + 5 < this.totalPages) { // Change 3 to 5
      this.currentPage = startPage + 5; // Change 3 to 5
      this.page = this.currentPage;
      this.handleSrollFunction();
    }
  }
  
   handleSrollFunction(){
    if (this.departmentsIds.length > 0) {
      this.handleSearchMajorsbyDepartments();
    } else if (this.temp_label) {
      this.searchByLabel();
    } else {
      this.handleSearchMajors();
    }
   }
  gotoPage(page: number): void {
    this.currentPage = page;
    this.page = page;
  this.handleSrollFunction()
  
  }
  

 

  getAllDepartments(){
    return this.departmentService.getDepartements().subscribe(
      data=>{
        this.departments=data
      }
    )
  }

  searchByLabel(){
    this.currentPage = 0;
    this.page = 0;
    if (this.temp_label==""){ 
      this.handleSearchMajors();
    }
    else{
      this.majorService.findByLabel(this.departmentsIds,this.page,this.size,this.temp_label).subscribe(
        data=>{
          this.majors = data.content;
            this.totalPages = data.totalPages;
            this.currentPage = data.number;
            this.setDisplayedPages();
           
        }
      )
    }

    
   
  }



}
