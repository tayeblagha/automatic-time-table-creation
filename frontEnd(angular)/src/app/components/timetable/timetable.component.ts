
import { ModuleElement } from '../../models/moduleElement.models';
import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Classes } from 'src/app/models/classes.models';
import { Department } from 'src/app/models/departement.models';
import { Major } from 'src/app/models/majors.models';
import { Semestre } from 'src/app/models/semestre.models';
import { ActionsService } from 'src/app/services/actions.service';
import { ClassesService } from 'src/app/services/classe.service';
import { DepartmentService } from 'src/app/services/department.service';
import { ScheduleService } from 'src/app/services/Schedule.service';
import { MajorService } from 'src/app/services/major.service';
import Swal from 'sweetalert2';
import { SchoolGeneralInfo } from 'src/app/models/school.models';
import { SchoolGeneralInfoService } from 'src/app/services/school-general-info.service';
import { SemesterService } from 'src/app/services/semester.service';

@Component({
  selector: 'app-timetable',
  templateUrl: './timetable.component.html',
  styleUrls: ['./timetable.component.css']
})
export class TimetableComponent implements OnInit {
 schoolGeneralInfo: SchoolGeneralInfo |undefined;
  semesters: Semestre[] | undefined = [] ;
  prof!: boolean;
  public departments:Department[] = [];
  public majors:Major[] = [];
  public moduleElement:ModuleElement[] = [];
  selectedDepartment: Department|undefined;
  selectedMajor: Major|undefined;
   selectedSemester: Semestre|undefined;
   classes!:Classes ;
  spinnerExport:boolean=false;
  ready = false;
  admin:boolean = false;
  constructor(
    private schoolGeneralInfoService:SchoolGeneralInfoService,private semesterService:SemesterService,
    private actons:ActionsService,private cookieService: CookieService,private departmentService: DepartmentService,private majorService: MajorService,private scheduleService:ScheduleService,private classeService:ClassesService) {
    
  }
  ngOnInit() {
    this.prof = (this.cookieService.get('role') == 'Teacher')? true : false; 
    if(this.prof){
       this.ready = true;
      this.scheduleService.getProfSchedule(parseFloat(this.cookieService.get("userId"))).subscribe(
        data=>{
          console.log(data);
          
          this.moduleElement = data;
          console.log(data);
        }
      )

    }
    else{
    this.admin = this.cookieService.check('role');


  }
    
  }






}

