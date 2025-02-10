import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { MajorService } from '../../../services/major.service';
import { Major } from '../../../models/majors.models';
import { Department } from '../../../models/departement.models';
import { DepartmentService } from '../../../services/department.service';
import { SemesterService } from 'src/app/services/semester.service';
import { Semestre } from 'src/app/models/semestre.models';
import { SchoolGeneralInfo } from 'src/app/models/school.models';
import { SchoolGeneralInfoService } from 'src/app/services/school-general-info.service';

@Component({
  selector: 'app-add-new-major',
  templateUrl: './add-new-majors.component.html',
  styleUrls: ['./add-new-majors.component.css'],
})export class AddNewMajorComponent implements OnInit {
  major_id:any
  newMajor: Partial<Major> = {
    label: '',
    semesters: [],
    name: '',
    department: {
      id: -1,
      name:'',
      label: '',
      classes: [],
    },
  };
  departments: Department[] = [];
  selectedDepartmentId: number | null = null;
  schoolGeneralInfo: SchoolGeneralInfo |undefined;
  semesters: Semestre[] | undefined = [] ;
  dropdownSettings = {
    singleSelection: false,
    idField: 'id',
    textField: 'number',
    selectAllText: 'Select All',
    unSelectAllText: 'Unselect All',
    itemsShowLimit: 3,
    allowSearchFilter: true,
  };

  constructor(
    private majorService: MajorService,
    private departementService: DepartmentService,
    private semesterService:SemesterService,
    private schoolGeneralInfoService:SchoolGeneralInfoService,
    private router: Router,
    private activatedRoute:ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.getDepartements();
    this.loadSchoolData();
    this.major_id=this.activatedRoute.snapshot.params["major_id"]

    if (this.major_id){
      this.getMajorById(this.major_id);
    }

  }

  getDepartements() {
    this.departementService.getDepartements().subscribe((data: Department[]) => {
      this.departments = data;
    });
  }
  handleAddMajor() {
    if (this.newMajor.label && this.newMajor.name) {
      const majorWithDepartment: Major = {
        ...this.newMajor,
        department: {
          id: Number(this.selectedDepartmentId),
        },
      } as Major;
  
      const observer = {
        next: (message: string) => {
          Swal.fire('Success', message, 'success');
          this.router.navigateByUrl('/majors');
        },
        error: (err: any) => {
          console.error(err);
          Swal.fire('Error', 'An error occurred while processing the request.', 'error');
        },
      };
  
      if (this.major_id) {
        // Update existing major
        this.majorService
          .updateMajor(this.major_id, majorWithDepartment)
          .subscribe({
            next: () => observer.next('Major updated successfully'),
            error: observer.error,
          });
      } else {
        // Save new major
        this.majorService
          .saveMajor(majorWithDepartment)
          .subscribe({
            next: () => observer.next('Major added successfully'),
            error: observer.error,
          });
      }
    } else {
      // Validation error
      Swal.fire('Error', 'Please fill in all fields of the form correctly.', 'error');
    }
  }
  
  // Using async/await to ensure sequential execution
  async loadSchoolData(): Promise<void> {
    await this.getSchoolGeneralInfo();
    await this.getSemesters();
  }

  // Get school general info
  async getSchoolGeneralInfo(): Promise<void> {
    try {
      const data = await this.schoolGeneralInfoService.getSchoolGeneralInfo().toPromise();
      this.schoolGeneralInfo = data;
    } catch (error) {
      console.error('Error fetching school general info', error);
    }
  }

  // Get semesters
  async getSemesters(): Promise<void> {
    if (this.schoolGeneralInfo?.numberSemesters) {
      try {
        const data = await this.semesterService.getFirstNSemesters(Number(this.schoolGeneralInfo.numberSemesters)).toPromise();
        this.semesters = data;
      } catch (error) {
        console.error('Error fetching semesters', error);
      }
    }
  }

  getMajorById(id:any){
    this.majorService.getMajor(id).subscribe(
      data=>{
        this.newMajor=data 
        this.selectedDepartmentId=Number(this.newMajor.department?.id )

        
      }
    )
  }



}
