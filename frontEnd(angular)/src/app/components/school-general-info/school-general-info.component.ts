import { Component, OnInit } from '@angular/core';
import { SchoolGeneralInfoService } from 'src/app/services/school-general-info.service';
import { SchoolGeneralInfo } from 'src/app/models/school.models';
import { Semestre } from 'src/app/models/semestre.models';
import { SemesterService } from 'src/app/services/semester.service';
@Component({
  selector: 'app-school-general-info',
  templateUrl: './school-general-info.component.html',
  styleUrls: ['./school-general-info.component.css']
})
export class SchoolGeneralInfoComponent implements OnInit {
  schoolGeneralInfo: SchoolGeneralInfo |undefined;
  semesters: Semestre[] | undefined = [] ;
  isEditing: boolean = false;
  yearRanges: string[] = [];
  current_year: any;

  constructor(private schoolGeneralInfoService: SchoolGeneralInfoService,
    private semesterService: SemesterService) { }

  ngOnInit(): void {
    this.loadSchoolData();
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

  // Toggle edit mode
  toggleEditMode(): void {
    this.isEditing = !this.isEditing;
  }

  // Update school general info
  updateSchoolGeneralInfo(): void {
    if (this.schoolGeneralInfo) {
      const startYear = Number(this.current_year.split('/')[0]);
      this.schoolGeneralInfo.academicYear.startYear = startYear;
      this.schoolGeneralInfo.academicYear.endYear = startYear + 1;
      this.schoolGeneralInfoService.updateSchoolGeneralInfo(this.schoolGeneralInfo).subscribe(
        (updatedData) => {
          this.schoolGeneralInfo = updatedData;
          this.loadSchoolData()
          this.isEditing = false;  // Exit edit mode after successful update
        },
        (error) => {
          console.error('Error updating school general info', error);
        }
      );
    }
  }

  generateYearRanges(): void {
    const currentYear = new Date().getFullYear();
    const startYear = currentYear - 3;
    const endYear = currentYear + 3;
    this.current_year = `${this.schoolGeneralInfo?.academicYear.startYear}/${this.schoolGeneralInfo?.academicYear.endYear}`;
    for (let year = startYear; year <= endYear; year++) {
      const slashRange = `${year}/${year + 1}`;
      this.yearRanges.push(slashRange);
    }
  }

  handleUpdate(): void {
    this.isEditing = true;
    this.generateYearRanges();
  }

  updateSemester(semester:any) {
    this.semesterService.updateSemester(semester.id,semester).subscribe(
      data=>{
        this.getSemesters();
      }
    )
  }
}
