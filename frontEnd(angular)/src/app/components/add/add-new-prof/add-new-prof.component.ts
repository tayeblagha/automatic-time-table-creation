import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import { Department } from 'src/app/models/departement.models';
import { Major } from 'src/app/models/majors.models';
import { Prof } from 'src/app/models/prof.models';
import { DepartmentService } from 'src/app/services/department.service';
import { MajorService } from 'src/app/services/major.service';
import { ProfServiceService } from 'src/app/services/prof-service.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-new-prof',
  templateUrl: './add-new-prof.component.html',
  styleUrls: ['./add-new-prof.component.css']
})
export class AddNewProfComponent implements OnInit {
  teacher_id:any
  newProf: Prof = {
    firstName: '',
    lastName: '',
    email: '',
    login: '',
    password: '',
    maxHoursPerWeek: 20,
    majors: [],
    departments: [],
    reservedHours:0
  };
  passwordFieldType: string = 'password';

togglePasswordVisibility() {
  this.passwordFieldType =
    this.passwordFieldType === 'password' ? 'text' : 'password';
}


  // Dropdown data and selections
  departments: Department[] = [];
  selectedDepartments: Department[] = [];
  majors: Major[] = [];
  selectedMajors: Major[] = [];

  dropdownSettings: IDropdownSettings = {};

  majordownSettings: IDropdownSettings = {};

  constructor(
    private profService: ProfServiceService,
    private majorService: MajorService,
    private departmentService: DepartmentService,
    private router: Router,
    private activatedRoute:ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.teacher_id=this.activatedRoute.snapshot.params["teacher_id"]
    if (this.teacher_id){
      this.profService.getProf(this.teacher_id).subscribe(
        data=>{
          this.newProf=data
          this.selectedDepartments=this.newProf.departments || []
          this.selectedMajors=this.newProf.majors || []
          this.onDepartmentChange()
        }
      )
    }

    this.getDepartments();
    this.dropdownSettings = {
      singleSelection: false,
      idField: 'id',
      textField: 'name',
      selectAllText: 'Select All',
      unSelectAllText: 'Unselect All',
      itemsShowLimit: 3,
      allowSearchFilter: true,
    };

    this.majordownSettings = {
      singleSelection: false,
      idField: 'id',
      textField: 'nameAndDepartment',
      selectAllText: 'Select All',
      unSelectAllText: 'Unselect All',
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
    const selectedDepartmentIds = this.selectedDepartments.map((d) => d.id);
    console.log(this.selectedDepartments);
    console.log(this.selectedMajors);

    if (selectedDepartmentIds.length > 0) {
      try {
        const majorsData = await this.majorService.findByDepartmentsIds(selectedDepartmentIds).toPromise();
        // Add displayName to each major
        this.majors = majorsData || [];

        const tempMajors: any[] = [];

        // Loop through selectedMajors and fetch the departmentId
        for (const major of this.selectedMajors) {
          const majorDetails = await this.getMajorById(major.id);


          console.log(majorDetails)
          // If the departmentId of the major is in the selectedDepartmentIds, add it
          if (selectedDepartmentIds.includes(majorDetails?.department.id || -1)) {
            tempMajors.push(major);
          }
        }

        // Assign the filtered majors to selectedMajors
        this.selectedMajors = [...tempMajors];
       
      } catch (err) {
        console.error('Error fetching majors:', err);
        Swal.fire('Error', 'Failed to load majors for selected departments', 'error');
      }
    } else {
      this.majors = []; // Clear majors if no departments are selected
      this.selectedMajors = []; // Optionally clear selected majors
    }
}

  
  

  // Save new professor
  handleAddProf(): void {
    if (
      this.newProf.firstName &&
      this.newProf.lastName &&
      this.newProf.email &&
      this.newProf.login &&
      this.newProf.password &&
      this.selectedDepartments.length > 0
    ) {
      const newProf: Prof = {
       ...this.newProf,
        majors: this.selectedMajors, // Selected majors
        departments:this.selectedDepartments
       
      };
        
      this.profService.saveProf(newProf).subscribe({
        next: () => {
          Swal.fire('Success', 'Professor saved successfully', 'success');
          this.router.navigateByUrl('/teachers');
        },
        error: (err) => {
          console.error('Error saving professor:', err);
          Swal.fire('Error', 'Failed to add professor', 'error');
        },
      });
    } else {
      Swal.fire('Error', 'Please fill in all fields of the form correctly', 'error');
    }
  }
  async getMajorById(id: any) {
    return await this.majorService.getMajor(id).toPromise();
  }
}
