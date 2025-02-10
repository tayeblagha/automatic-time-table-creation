import { DepartmentService } from 'src/app/services/department.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Department } from 'src/app/models/departement.models';
import Swal from 'sweetalert2';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-add-new-departement',
  templateUrl: './add-new-departement.component.html',
  styleUrls: ['./add-new-departement.component.css']
})
export class AddNewDepartementComponent implements OnInit {
  newDepartementFormGroup!: FormGroup;
  departmet_id: any;

  constructor(
    private fb: FormBuilder,
    private dpService: DepartmentService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.newDepartementFormGroup = this.fb.group({
      label: this.fb.control(null, [Validators.required]),
      name: this.fb.control(null, [Validators.required])
    });

    // Check if an ID is provided in the route
    this.departmet_id = this.activatedRoute.snapshot.params['id'];
    if (this.departmet_id) {
      this.getDepartmentById();
    }
  }

  handleAddDepartement() {
    if (this.newDepartementFormGroup.valid) {
      const department: Department = this.newDepartementFormGroup.value;

      if (this.departmet_id) {
        // Update existing department
        this.dpService.updateDepartment(this.departmet_id, department).subscribe({
          next: () => {
            Swal.fire('Success', 'Department updated successfully', 'success');
            this.router.navigateByUrl('/departments');
          },
          error: err => {
            console.error(err);
            Swal.fire('Error', 'Failed to update department', 'error');
          }
        });
      } else {
        // Add new department
        this.dpService.saveDepartment(department).subscribe({
          next: () => {
            Swal.fire('Success', 'Department added successfully', 'success');
            this.router.navigateByUrl('/departments');
          },
          error: err => {
            console.error(err);
            Swal.fire('Error', 'Failed to add department', 'error');
          }
        });
      }
    } else {
      Swal.fire('Error', 'Please fill in all fields of the form correctly', 'error');
    }
  }

  getDepartmentById() {
    this.dpService.getDepartment(this.departmet_id).subscribe({
      next: data => {
        // Synchronize the form with the fetched data
        this.newDepartementFormGroup.patchValue(data);
      },
      error: err => {
        console.error(err);
        Swal.fire('Error', 'Failed to fetch department details', 'error');
      }
    });
  }
}
