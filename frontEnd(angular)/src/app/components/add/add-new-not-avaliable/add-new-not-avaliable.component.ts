import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { Observable } from 'rxjs';
import { NotAvailable } from 'src/app/models/notAvailable.models';
import { Prof } from 'src/app/models/prof.models';
import { NotAvaliableService } from 'src/app/services/not-avaliable.service';
import { ProfServiceService } from 'src/app/services/prof-service.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-new-not-avaliable',
  templateUrl: './add-new-not-avaliable.component.html',
  styleUrls: ['./add-new-not-avaliable.component.css']
})
export class AddNewNotAvaliableComponent implements OnInit {

  newNotAvaliableFormGroup !: FormGroup;
  teacherId !: number;
  prof!: Prof;


  constructor(
    private fb:FormBuilder,
    private service:NotAvaliableService,
    private router:Router,
    private cookieService:CookieService,
    private profService:ProfServiceService
  ){}

  ngOnInit(): void {
    let id = parseFloat(this.cookieService.get('userId'));
    this.getProf(id);
    this.newNotAvaliableFormGroup = this.fb.group({
      day: this.fb.control(null,[Validators.required]),
      period:this.fb.control(null,[Validators.required])
    });
  }

  getProf(id: number){
    this.profService.getProf(id).subscribe(
      (prof: Prof) => {
        this.prof = prof;
      }
    );
  }

  handleAddNotAvaliable() {
    if (this.newNotAvaliableFormGroup.valid) {
    const newNotAvaliable: NotAvailable = this.newNotAvaliableFormGroup.value;
    this.service.createNotAvaliable(newNotAvaliable).subscribe({
      next: data => {
        Swal.fire('Succes', 'Not Avaliable added successfully', 'success');
        this.router.navigateByUrl('/notAvaliables');
      },
      error: err => {
        console.log(err);
      }
    });
  } else {
    Swal.fire('Error', 'Please fill in all fields of the form correctly', 'error');
  }
  }
}
