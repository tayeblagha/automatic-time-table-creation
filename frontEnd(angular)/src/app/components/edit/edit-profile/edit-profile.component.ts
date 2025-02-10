import { Component } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { Prof } from 'src/app/models/prof.models';
import { ProfServiceService } from 'src/app/services/prof-service.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent {
editProfFormGroup!: FormGroup;
  prof!: Prof;

  
  constructor(private profService: ProfServiceService,
    private fb: FormBuilder,
    private router: Router,private route : ActivatedRoute) {
    this.prof=this.router.getCurrentNavigation()?.extras.state as Prof;
  }

  ngOnInit(): void {
    this.editProfFormGroup = this.fb.group({
      firstName: [''],
      lastName: [''],
      email: [''],
      login: [''],
      password: ['']
    });

    this.setFormValues();
  }

  setFormValues() {
    if (this.prof) {
      this.editProfFormGroup.patchValue({
        firstName: this.prof.firstName,
        lastName: this.prof.lastName,
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
         Swal.fire('Success', 'Profile updated successfully', 'success');
        this.router.navigateByUrl('/home');
      });
    }
  }
}
