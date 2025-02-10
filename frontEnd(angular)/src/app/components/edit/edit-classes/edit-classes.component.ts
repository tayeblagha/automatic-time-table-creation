import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import Swal from "sweetalert2";
import {ClassesService} from "../../../services/classe.service";
import {Classes} from "../../../models/classes.models";

@Component({
  selector: 'app-edit-classe',
  templateUrl:'./edit-classes.component.html',
  styleUrls: ['./edit-classes.component.css']
})
export class EditClassesComponent implements OnInit {
  editClassesFormGroup!: FormGroup;
  classes!: Classes;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private clService: ClassesService
  ) {
    this.classes = this.router.getCurrentNavigation()?.extras.state as Classes;
  }

  ngOnInit(): void {
    this.editClassesFormGroup = this.fb.group({
      label: [''],
      numberOfStudents: [''],
      major: ['']
    });
    this.setFormValues();
  }
  setFormValues() {
    if (this.classes) {
      this.editClassesFormGroup.patchValue({
        label: this.classes.label,
        numberOfStudents: this.classes.numberOfStudents,
        majors: this.classes.majors
      });
    }
  }
  handleUpdateClasse() {

    if (this.editClassesFormGroup.valid && this.classes) {
      const updatedClasse: Classes = {
        ...this.classes,
        ...this.editClassesFormGroup.value
      };
      this.clService.updateClasse(updatedClasse.id,updatedClasse).subscribe((data) => {
        Swal.fire( 'Success', 'Class updated successfully', 'success');
          this.router.navigateByUrl('/classes');
        }

      );

    }
  }

}
