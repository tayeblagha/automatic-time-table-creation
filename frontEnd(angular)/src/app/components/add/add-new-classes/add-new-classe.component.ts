import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { Classes } from '../../../models/classes.models';
import { ClassesService } from '../../../services/classe.service';
import { Major } from '../../../models/majors.models';
import { MajorService } from '../../../services/major.service';
import { ActivatedRoute, Router } from '@angular/router';
import { DepartmentService } from 'src/app/services/department.service';
import { Department } from 'src/app/models/departement.models';

@Component({
  selector: 'app-add-new-classe',
  templateUrl: './add-new-classe.component.html',
  styleUrls: ['./add-new-classe.component.css'],
})
export class AddNewClasseComponent implements OnInit {
  selected_major_id: any;
  newClass: Partial<Classes> = {
    name: '',
    numberOfStudents:20,
    grade:0,
    
  };
  majors: Major[] = [];

  departments: Department[] = [];
  selectedDepartmentId: Number | undefined;

  class_Id:any

  constructor(
    private clService: ClassesService,
    private majorService: MajorService,
    private departementService: DepartmentService,
    private router:Router,
    private activatedRoute:ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.fetchMajors();
    this.getDepartements()
    this.class_Id=this.activatedRoute.snapshot.params["id"]
    if (this.class_Id){
      this.getClass()
      
    }
  }

  fetchMajors(): void {
    this.majorService.getAllMajors().subscribe(
      (majors: Major[]) => {
        this.majors = majors;
        // if (this.majors.length > 0) {
        //   this.selected_major_id = this.majors[0].id;
        //   this.newClass.major = this.majors[0];
        // }
      },
      (error) => {
        console.error('Error fetching majors:', error);
        Swal.fire('Error', 'Unable to fetch majors. Please try again later.', 'error');
      }
    );
  }

  

  


  handleAddClasses(): void {
    //console.log(this.newClass)
    //this.newClass.majors=this.majors
//    console.log(this.selectedDepartmentId)
  //  console.log(this.newClass)

    if (
      this.newClass.name &&
      this.newClass.numberOfStudents 
    ) {
      this.clService.saveClasse(this.newClass as Classes,this.selectedDepartmentId).subscribe(
        () => {
          Swal.fire('Success', 'Class saved successfully', 'success');
          // Reset the form
          this.newClass = {
            label: '',
            numberOfStudents: 0,
            grade:0,
            majors:  [] 
          };
          this.router.navigateByUrl('/classes');
        },
        (error) => {
          console.error('Error saving class:', error);
          Swal.fire('Error', 'Unable to save the class. Please try again.', 'error');
        }
      );
    } else {
      Swal.fire(
        'Error',
        'Please fill in all fields of the form correctly',
        'error'
      );
    }
  }

  getDepartements() {
    this.departementService.getDepartements().subscribe((data: Department[]) => {
      this.departments = data;
    });
  }
  getClass(){
    this.clService.getClasse(this.class_Id).subscribe(
      data=>{
        this.newClass=data
        this.selectedDepartmentId=this.newClass.department?.id
      }
    )
  }
}
