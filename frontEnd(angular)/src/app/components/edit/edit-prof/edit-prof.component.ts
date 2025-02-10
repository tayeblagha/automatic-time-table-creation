import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Prof } from 'src/app/models/prof.models';
import { ProfServiceService } from 'src/app/services/prof-service.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-edit-prof',
  templateUrl: './edit-prof.component.html',
  styleUrls: ['./edit-prof.component.css']
})
export class EditProfComponent implements OnInit {
  editProfFormGroup!: FormGroup;
  prof!: Prof;

  
  constructor(private profService: ProfServiceService,
    private fb: FormBuilder,
    private router: Router,private route : ActivatedRoute) {
    this.prof=this.router.getCurrentNavigation()?.extras.state as Prof;
  }

  ngOnInit(): void {
    this.editProfFormGroup = this.fb.group({
      lastName: [''],
      firstName: [''],
      email: [''],
      login: [''],
      password: ['']
    });

    this.setFormValues();
  }

  setFormValues() {
    if (this.prof) {
      this.editProfFormGroup.patchValue({
        lastName: this.prof.lastName,
        firstName: this.prof.firstName,
        email: this.prof.email,
        login: this.prof.login,
        password: this.prof.password
      });
    }
  }

  handleUpdateProf() {
    if (this.editProfFormGroup.valid && this.prof) {
      const updatedProf: Prof = {
        ...this.prof,
        ...this.editProfFormGroup.value
      };

      this.profService.updateProf(updatedProf.id,updatedProf).subscribe((data) => {
        Swal.fire('Success', 'Teacher updated successfully', 'success');
        this.router.navigateByUrl('/teachers');
      });
    }
  }
}
