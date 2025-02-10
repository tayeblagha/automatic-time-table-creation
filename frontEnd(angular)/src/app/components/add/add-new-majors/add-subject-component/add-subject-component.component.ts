import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, RouterOutlet } from '@angular/router';
import { GradeDuration } from 'src/app/models/gradeDuration.models';
import { Major } from 'src/app/models/majors.models';
import { MajorService } from 'src/app/services/major.service';
import Swal from 'sweetalert2';

// Updated GradeDuration interface with 'durationPerWeek'


@Component({
  selector: 'app-add-subject-component',
  templateUrl: './add-subject-component.component.html',
  styleUrls: ['./add-subject-component.component.css'],
})
export class AddSubjectComponentComponent implements OnInit {
  subjectForm: FormGroup;
  existingGradeDurations: GradeDuration[]  = []; // Example initial data
  isAddingGrade = false; // Flag to control visibility of the grade/duration input fields
  major: Major | undefined = undefined;
  major_id: any;
  major_name:any
  constructor(
    private fb: FormBuilder,
    private majorService: MajorService,
    private routerOutlet: RouterOutlet,
    private route: Router
  ) {
    this.subjectForm = this.fb.group({
      gradeDurations: this.fb.array([]), // Initialize an empty FormArray
    });
  }

  ngOnInit(): void {
    this.major_id = this.routerOutlet.activatedRoute.snapshot.params['id'];
    if (this.major_id) {
      // Assuming getMajorById is still async, we handle the promise without async/await
      this.getMajorById(this.major_id).then(major => {
        // Ensure that major and gradeDurations exist before assigning
        if (major && major.gradeDurations) {
          this.existingGradeDurations = major.gradeDurations;
          this.major_name=major.name
          
        }
      }).catch(error => {
        // Handle any errors if necessary
        console.error(error);
      });
    }

    
  }

  get gradeDurations(): FormArray {
    return this.subjectForm.get('gradeDurations') as FormArray;
  }

  addGradeDuration(): void {
    const gradeDurationGroup = this.fb.group({
      grade: [null, [Validators.required, Validators.min(1), Validators.max(5)]],
      durationPerWeek: [null, [Validators.required, Validators.min(1)]],
    });
    this.gradeDurations.push(gradeDurationGroup);
    this.isAddingGrade = true; // Show input fields when adding grade
  }

  removeGradeDuration(index: number): void {
    this.gradeDurations.removeAt(index);
  }

  deleteGrade(grade: number): void {
    const index = this.existingGradeDurations.findIndex((item) => item.grade === grade);
    if (index !== -1) {
      this.existingGradeDurations.splice(index, 1); // Remove the grade
    }
  }

  onSubmit(): void {
    if (this.subjectForm.valid) {
      // Ensure grades are unique and prevent duplicates
      this.gradeDurations.controls.forEach((control) => {
        const grade = control.value.grade;
        const durationPerWeek = control.value.durationPerWeek;

        // Check if the grade already exists in the existing list
        const existingGradeIndex = this.existingGradeDurations.findIndex(
          (item) => item.grade === grade
        );

        if (existingGradeIndex === -1) {
          // If grade doesn't exist, add new grade/duration pair to the existing list
          this.existingGradeDurations.push({ grade, durationPerWeek });
        } else {
          // If grade exists, update its duration
          this.existingGradeDurations[existingGradeIndex].durationPerWeek = durationPerWeek;
        }
      });

      // Sort the list in ascending order based on grade
      this.existingGradeDurations.sort((a, b) => a.grade - b.grade);

      console.log('Form submitted:', this.subjectForm.value);

      // Reset the form after submission
      this.subjectForm.reset();
      // Optionally, clear the gradeDurations FormArray
      while (this.gradeDurations.length) {
        this.gradeDurations.removeAt(0);
      }

      // Hide input fields after submission
      this.isAddingGrade = false;
    }
  }

  getMajorById(id: any): Promise<Major> {
    return new Promise((resolve, reject) => {
      this.majorService.getMajor(id).subscribe(
        (data: Major) => resolve(data),
        (error) => reject(error)
      );
    });
  }
  updateGadeDuration(){
    this.majorService.updateMajorGradeDuration(this.major_id,this.existingGradeDurations).subscribe(
      data=>{
        Swal.fire('Success',this.major_name+ "' Major updated successfully", 'success');
        this.major=data
      }
    )
      }
  
  
}
