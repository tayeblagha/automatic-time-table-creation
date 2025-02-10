import { RoomService } from 'src/app/services/classroom.service';
import { DepartmentService } from 'src/app/services/department.service';
import { ProfServiceService } from 'src/app/services/prof-service.service';
import { Component, OnInit } from '@angular/core';
import { ClassesService } from 'src/app/services/classe.service';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent  implements OnInit
{
  numDepartments:number=0;
  numProfs:number=0;
  numClasses:number=0;
  numRooms:number=0;

  
    constructor(private dpService:DepartmentService,private prfService:ProfServiceService,private clsService:ClassesService,private salleService: RoomService) { }
  
    ngOnInit(): void {
      this.getNbDepartements();
      this.getNbProfs();
      this.getNbClasses();
      this.getNbRooms();
    }
    getNbDepartements() {
       this.dpService.searchDepartments("",0,6).subscribe(
      (data) => {
        this.numDepartments= data.totalElements;
      }
    );
    }
    getNbProfs() {
      this.prfService.getProfs(0,6).subscribe(
      (data) => {
        this.numProfs= data.totalElements;
      }
    );
    }
    getNbClasses() {
      this.clsService.getClasses(0,6).subscribe(
      (data) => {
        this.numClasses= data.totalElements;
      }
    );
    }
    getNbRooms() {
      this.salleService.getRooms(0,6).subscribe(
      (data) => {
        this.numRooms= data.totalElements;
      }
    );
    }


}
