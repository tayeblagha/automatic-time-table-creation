import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import Swal from "sweetalert2";
import {MajorService} from "../../../services/major.service";
import {Major} from "../../../models/majors.models";
@Component({
  selector: 'app-edit-major',
  templateUrl: './edit-major.component.html',
  styleUrls: ['./edit-major.component.css']
})
export class EditMajorComponent implements OnInit {
  editMajorFormGroup!: FormGroup;
  major!: Major;
  constructor(
    private fb: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private dpService: MajorService
  ) {
    this.major = this.router.getCurrentNavigation()?.extras.state as Major;
  }
  ngOnInit(): void {
    this.editMajorFormGroup = this.fb.group({
      label: [''],
      numOfSem: [''],
      department: ['']
    });
    this.setFormValues();
  }
  setFormValues() {
    if (this.major) {
      this.editMajorFormGroup.patchValue({
        label: this.major.label,
        department: this.major.department.label
      });
    }
  }
  handleUpdateMajor() {

    if (this.editMajorFormGroup.valid && this.major) {
      const updatedMajor: Major = {
        ...this.major,
        ...this.editMajorFormGroup.value
      };
      this.dpService.updateMajor(updatedMajor.id,updatedMajor).subscribe((data) => {
        Swal.fire( 'Succes', 'Major updated successfully','success');
        this.router.navigateByUrl('/majors');
        }

      );

    }
  }
}
