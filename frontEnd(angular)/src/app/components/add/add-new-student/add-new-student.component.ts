import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import { Classes } from 'src/app/models/classes.models';
import { Department } from 'src/app/models/departement.models';
import { Student } from 'src/app/models/student.models';
import { ClassesService } from 'src/app/services/classe.service';
import { DepartmentService } from 'src/app/services/department.service';
import { StudentService } from 'src/app/services/student.service ';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-new-student',
  templateUrl: './add-new-student.component.html',
  styleUrls: ['./add-new-student.component.css']
})
export class AddNewStudentComponent {
  classes:Classes[]=[]
  grade:number=1
 student_id:any
 selectedDepartment:Department[] =[]
 selectedClass:Partial<Classes>[]=[]
  newStudent: Student = {
    firstName: '',
    lastName: '',
    email: '',
    login: '',
    password: '',
    
  };

  passwordFieldType: string = 'password';

togglePasswordVisibility() {
  this.passwordFieldType =
    this.passwordFieldType === 'password' ? 'text' : 'password';
}


  // Dropdown data and selections
  departments: Department[] = [];

  dropdownSettings: IDropdownSettings = {};

  classesdownSettings: IDropdownSettings = {};

  constructor(
    private studentService: StudentService,
    private classService:ClassesService,
    private departmentService: DepartmentService,
    private router: Router,
    private activatedRoute:ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.student_id=this.activatedRoute.snapshot.params["student_id"]
    if (this.student_id){
      this.studentService.getStudentById(this.student_id).subscribe(
        data=>{
          this.newStudent=data
          if (this.newStudent.classes){
            if (this.newStudent.classes?.department)
            this.selectedDepartment=[this.newStudent.classes?.department]
            this.selectedClass=[this.newStudent.classes]
            this.grade=Number(this.newStudent.classes.grade)
          }
         
          
          this.onDepartmentChange()
        }
      )
    }

    this.getDepartments();
    this.dropdownSettings = {
      singleSelection: true,
      idField: 'id',
      textField: 'name',
      itemsShowLimit: 3,
      allowSearchFilter: true,
    };

 


  }

  // Fetch the list of departments
  getDepartments(): void {
    this.departmentService.getDepartements().subscribe({
      next: (data) => {
        this.departments = data;
      },
      error: (err) => {
        console.error('Error loading departments:', err);
        Swal.fire('Error', 'Failed to load departments', 'error');
      },
    });
  }


  
  // Fetch majors for selected department IDs
  async onDepartmentChange(): Promise<void> {
    //console.log(this.selectedDepartment,this.selectedDepartment[0]?.id)

    if (this.selectedDepartment && this.grade) {
      try {
        
          this.classService.getClassesByDepartmentandGrade(this.selectedDepartment[0]?.id,this.grade).subscribe(
            data=>{
              this.classes=data
            }
          )
   

      } catch (err) {
        console.error('Error fetching classes:', err);
        Swal.fire('Error', 'Failed to load classes for selected department', 'error');
      }
    } else {
      this.selectedDepartment=[]
      this.classes=[]
    }
}

onDepartmentunselect(){
  this.selectedDepartment=[]
  this.classes=[]
}

  
  

  // Save new professor
  handleAddStudent(): void {
   
    if (
      this.newStudent.firstName &&
      this.newStudent.lastName &&
      this.newStudent.email &&
      this.newStudent.login &&
      this.newStudent.password &&
      this.selectedClass){
      
      
     
      this.studentService.createStudent(this.newStudent,this.selectedClass[0].id).subscribe({
        next: () => {
          Swal.fire('Success', 'Student saved successfully', 'success');
          this.router.navigateByUrl('/students');
        },
        error: (err) => {
          console.error('Error saving student:', err);
          Swal.fire('Error', 'Failed to add student', 'error');
        },
      });
    } else {
      Swal.fire('Error', 'Please fill in all fields of the form correctly', 'error');
    }
  }

  gradeChange(){
    this.selectedClass=[]
    this.onDepartmentChange()
  }



  
}
