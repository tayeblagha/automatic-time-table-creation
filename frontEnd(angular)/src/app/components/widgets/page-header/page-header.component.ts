import { Component, Input, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { SchoolGeneralInfo } from 'src/app/models/school.models';
import { SchoolGeneralInfoService } from 'src/app/services/school-general-info.service';
@Component({
  selector: 'app-page-header',
  templateUrl: './page-header.component.html',
  styleUrls: ['./page-header.component.css']
})
export class PageHeaderComponent implements OnInit{
  name!: string;
@Input() link!: string;
@Input() prev!: string;
@Input() current!: string;
schoolGeneralInfo:SchoolGeneralInfo |undefined=undefined
constructor(private cookieService: CookieService,
  private schoolGeneralInfoService:SchoolGeneralInfoService
) {}

ngOnInit() {
 this.name = this.cookieService.get('username');
 this.loadSchoolData()
// Use the stored values as needed
}

// Using async/await to ensure sequential execution
async loadSchoolData(): Promise<void> {
  await this.getSchoolGeneralInfo();
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

}
