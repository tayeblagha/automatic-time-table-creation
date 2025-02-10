import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Department } from 'src/app/models/departement.models';
import { DepartmentService } from 'src/app/services/department.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-edit-departement',
  templateUrl: './edit-departement.component.html',
  styleUrls: ['./edit-departement.component.css']
})
export class EditDepartementComponent implements OnInit {
  editDepartFormGroup!: FormGroup;
  depart!: Department;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private dpService: DepartmentService
  ) {
    this.depart = this.router.getCurrentNavigation()?.extras.state as Department;
  }

  ngOnInit(): void {
    this.editDepartFormGroup = this.fb.group({
      label: [''],
      chefDepartment: ['']
    });
    this.setFormValues();
  }
  setFormValues() {
    if (this.depart) {
      this.editDepartFormGroup.patchValue({
        label: this.depart.label,
      });
    }
  }


  handleUpdateDepart() {
    
    if (this.editDepartFormGroup.valid && this.depart) {
      const updatedDepart: Department = {
        ...this.depart,
        ...this.editDepartFormGroup.value
      };
      this.dpService.updateDepartment(updatedDepart.id,updatedDepart).subscribe((data) => {
        Swal.fire( 'Success', 'Department updated successfully','success');
        this.router.navigateByUrl('/departments');
        }
      
      );

    }
  }
}
