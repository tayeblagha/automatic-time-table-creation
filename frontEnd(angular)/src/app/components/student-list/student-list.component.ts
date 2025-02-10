import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Classes } from 'src/app/models/classes.models';
import { Student } from 'src/app/models/student.models';
import { ClassesService } from 'src/app/services/classe.service';
import { StudentService } from 'src/app/services/student.service ';

@Component({
  selector: 'app-student-list',
  templateUrl: './student-list.component.html',
  styleUrls: ['./student-list.component.css']
})
export class StudentListComponent  implements OnInit {
  class_id:any 
  students:Student[]=[]
  current_classe:Classes|undefined
  constructor(
    private activatedRoute:ActivatedRoute,
    private studentService:StudentService,
    private classService:ClassesService,
    private router:Router
  ){

  }

  ngOnInit(): void {
   this.class_id = this.activatedRoute.snapshot.params["class_id"]
   if(this.class_id){
      this.studentService.findStudentsByClassId(this.class_id).subscribe(
          data=>{
            this.students=data
          }
      )
      this.classService.getClasse(this.class_id).subscribe(
        data=>{
          this.current_classe=data
        }
      )
      

   }
  }

  studentDetails(id: any) {
    this.router.navigate(['/students/details', id]);
  }
  
}
