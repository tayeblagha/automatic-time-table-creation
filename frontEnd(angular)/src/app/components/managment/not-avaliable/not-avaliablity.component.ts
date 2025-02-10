
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { NotAvailable } from 'src/app/models/notAvailable.models';
import { NotAvaliableService } from 'src/app/services/not-avaliable.service';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-not-avaliable',
  templateUrl: './not-avaliablity.component.html',
  styleUrls: ['./not-avaliablity.component.css']
})
export class NotAvaliablityComponent implements OnInit {

  prof!: boolean;
  notAvaliables: NotAvailable[]=[];
  errorMessage!: string;
  notAvaliableFormGroup!: FormGroup;
  page: number = 0;
  size: number = 6;
  totalPages: number = 0;
  currentPage: number = 0;
  totalelements:number=0;
  ready = false;
  displayedPages: number[] = [];
  

  constructor(
    private notAvaService:NotAvaliableService,
    private fb:FormBuilder,
    private router:Router,
    private cookieService:CookieService
  ){}

  ngOnInit(): void {
    this.prof = (this.cookieService.get('role') == 'Teacher')? true : false; 
    if(this.prof){
       this.ready = true;
      this.notAvaService.getNotAvaliableByTeacherId(parseFloat(this.cookieService.get("userId"))).subscribe(
        data=>{
          console.log(data);
          
          this.notAvaliables = data;
          console.log(data);
        }
      )

    }
  }

  formatPeriod(period: string): string {
    switch (period) {
        case 'P0':
            return '08:30 - 10:00';
        case 'P1':
            return '10:00 - 12:00';
        case 'P2':
            return '13:00 - 15:00';
        case 'P3':
            return '15:00 - 17:00';
        case 'P4':
            return '17:00 - 19:00';
        default:
            return 'Unknown Period';
    }
}


  

  handleGetNotAvaliables(): void{
    this.notAvaService.getAllNotAvaliables();
    console.log(this.notAvaliables);
  }

  handleGetNotAvaliablesTeacherById(): void{
    const userId = parseInt(this.cookieService.get("userId"));
    this.notAvaService.getNotAvaliableByTeacherId(userId);
    console.log(this.notAvaliables);
  }

  handleEditNotAvaliable(editNotAvaliable:NotAvailable){
    this.router.navigateByUrl("/notAvailables/edit",{state:editNotAvaliable})
  }

  handleDeleteNotAvaliable(notAvaliable: NotAvailable): void {
    Swal.fire({
      title: 'Are you sure',
      text: "You won't be able to go back !",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it !'
    }).then((result) => {
      if (result.isConfirmed) {
        this.notAvaService.deleteNotAvaliable(notAvaliable.id).subscribe();
         this.notAvaliables = this.notAvaliables.filter((d) => d.id !== notAvaliable.id);

      }
    });
  }

  setDisplayedPages(): void {
    this.displayedPages = [];
    const startPage = Math.floor(this.currentPage / 3) * 3;
    for (
      let i = startPage;
      i < startPage + 3 && i < this.totalPages;
      i++
    ) {
      this.displayedPages.push(i);
    }
  }

   /*gotoPage(page: number): void {
    this.currentPage = page;
    this.page = page;
    this.handleSearchDepartments();
  }

  goToPreviousSet(): void {
    const startPage = Math.floor(this.currentPage / 3) * 3;
    if (startPage - 3 >= 0) {
      this.currentPage = startPage - 3;
      this.page = this.currentPage;
      this.handleSearchDepartments();
    }
  }

  goToNextSet(): void {
    const startPage = Math.floor(this.currentPage / 3) * 3;
    if (startPage + 3 < this.totalPages) {
      this.currentPage = startPage + 3;
      this.page = this.currentPage;
      this.handleSearchDepartments();
    }
  }
 notAvaliables: NotAvailable[] = [
    {
      id: 1,
      day: 'Monday',
      teacher: {
        id: 1, firstName: 'Ali', lastName: 'Ali',
        email: '',
        login: '',
        password: '',
      },
      period: '08:30-10:00'
    },
    {
      id: 2,
      day: 'Thursday',
      teacher: {
        id: 1, firstName: 'Alperen', lastName: 'Ali',
        email: '',
        login: '',
        password: '',

      },
      period: '08:30-10:00'
    },
    {
      id: 3,
      day: 'Tuesday',
      teacher: {
        id: 1, firstName: 'AAA', lastName: 'dad',
        email: '',
        login: '',
        password: '',

      },
      period: '08:30-10:00'
    },
    {
      id: 4,
      day: 'Friday',
      teacher: {
        id: 1, firstName: 'Ali', lastName: 'Ali',
        email: '',
        login: '',
        password: '',
      },
      period: '08:30-10:00'
    },
  ];*/


}