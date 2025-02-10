import { Component, OnInit, Optional } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { Classes } from 'src/app/models/classes.models';
import { Major } from 'src/app/models/majors.models';
import { Prof, TeacherHashMap } from 'src/app/models/prof.models';
import { TeacherClasseMajor } from 'src/app/models/teacherClasseMajor.models ';
import { ClassesService } from 'src/app/services/classe.service';
import { MajorService } from 'src/app/services/major.service';
import { ProfServiceService } from 'src/app/services/prof-service.service';
import { TeacherClassMajorsService } from 'src/app/services/TeacherClassMajors.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-major-grade',
  templateUrl: './major-grade.component.html',
  styleUrls: ['./major-grade.component.css']
})
export class MajorGradeComponent implements OnInit {
  selected_teacher:any
  selected_major:any
  tcmData: TeacherHashMap = {}; // Attribute to store the HashMap
  availableTeacher:Prof[]=[];
  class_id:any
  currentClass:Classes|undefined
  majors: Major[] = [];
  filteredMajors: Major[] = [];
  selectedSemesters: any[] = [];
  semesterOptions: any[] = [];
  dropdownSettings: any = {};
  grade_id: any;
  department_id: any;

  constructor(
    private majorService: MajorService,
    private routerOutlet: RouterOutlet,
    private classService:ClassesService,
    private profService:ProfServiceService,
    private teacherClassMajorsService:TeacherClassMajorsService,
    private route: Router
  ) {}

  ngOnInit(): void {
    this.grade_id = this.routerOutlet.activatedRoute.snapshot.params['grade_id'];
    this.department_id = this.routerOutlet.activatedRoute.snapshot.params['department_id'];
    this.class_id= this.routerOutlet.activatedRoute.snapshot.params['class_id'];  

    this.getMajorsBySemesters();
    this.getClass()
    // Configure dropdown settings
    this.dropdownSettings = {
      singleSelection: false,
      idField: 'number',
      textField: 'number',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 3,
      allowSearchFilter: true,
    };
    
  }

  getMajorsBySemesters() {
    this.majorService.findByGradeDurationGrade(this.grade_id, this.department_id).subscribe((data) => {
      this.majors = data;
      this.filteredMajors = [...this.majors]; // Default to showing all majors
  
      // Prepare unique semester options for the dropdown
      const allSemesters = this.majors.flatMap((major) => major.semesters);
      const uniqueSemesters = Array.from(
        new Map(allSemesters.map((semester) => [semester.number, semester])).values()
      );
  
      this.semesterOptions = uniqueSemesters.map((semester) => ({
        number: semester.number,
        id: semester.id,
      }));
    });
  }
  

  onSemesterSelect(item: any) {
    this.filterMajors();
  }

  onSemesterDeselect(item: any) {
    this.filterMajors();
  }

  onSelectAll(items: any) {
    this.filterMajors();
  }

  onDeselectAll(items: any) {
    this.filterMajors();
  }

  filterMajors() {
    if (this.selectedSemesters.length === 0) {
      this.filteredMajors = [...this.majors];
    } else {
      this.filteredMajors = this.majors.filter((major) =>
        major.semesters.some((semester) =>
          this.selectedSemesters.find((s) => s.number === semester.number)
        )
      );
    }
  }

  loadTCM(class_id: number): void {
    this.profService.getHashTCM(class_id).subscribe({
      next: (data: TeacherHashMap) => {
        this.tcmData = data;
        //console.log('Received HashMap:', this.tcmData);
      },
      error: (err) => {
        console.error('Error loading TCM data:', err);
      }
    });
  }
  getClass(){
    return this.classService.getClasse(this.class_id).subscribe(
      data=>{
       this.currentClass=data
       this.loadTCM(this.currentClass.id)
      }
    )
  }

  getHashTCM(){}

  getProfessor(id: any): string {
    // Check if the ID exists in tcmData
    if (this.tcmData[id]) {
      return this.tcmData[id].firstName +" "   +this.tcmData[id].lastName  ; // Return the teacher's name
    }
    return "Unassigned"; // Return "Unassigned" if the ID doesn't exist
  }
  test(){
    console.log("hi")
  }

   getTeachersByMajor(major_id: number){
    this.profService.getTeachersByMajor(major_id).subscribe(
      data=>{
        this.availableTeacher=data

        if (this.tcmData[major_id]) {
          this.selected_teacher = this.tcmData[major_id].id;
        } else if (this.availableTeacher.length > 0) {
          this.selected_teacher = this.availableTeacher[0].id; // Default to the first available teacher
        }
      }
    );
   }

   showTeacherList(major_id: any) {
    
    
    this.selected_major = major_id;
    this.getTeachersByMajor(major_id);

   
  }
  


   closeSelectDrop(){
    this.selected_major=undefined
   }

   
   handleAddTeacher() {
    const tcm: Partial<TeacherClasseMajor> = {
      classes: this.currentClass,
      major: {
        id: this.selected_major,
      },
      teacher: {
        id: this.selected_teacher,
      },
    };
   // Define the observer
   const observer = {
    next: (message: any) => {
      Swal.fire('Success', message, 'success');
      this.getClass()
      this.closeSelectDrop(); // Ensure this gets called on success
    },
    error: (err: any) => {
      console.error(err);
      Swal.fire('Error', 'An error occurred while processing the request.', 'error');
    },
  };
    if (!this.tcmData[this.selected_major]) {
  
      // Use the observer in the subscribe method
      this.teacherClassMajorsService.create(tcm).subscribe(observer);
    } else {
    
      this.teacherClassMajorsService.update(tcm,this.selected_major,this.currentClass?.id).subscribe(observer);
      // Update logic here
      //this.teacherClassMajorsService.update()
    }
  }
  

}

