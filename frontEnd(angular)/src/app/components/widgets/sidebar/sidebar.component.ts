import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit{
  active:number=0;
   sidebarItems:any[] = [];

  sidebarAdminItems = [
  {
    link: "/home",
    title: "Home",
    icon: "fas fa-home"
  },
  {
    link: "/schedules",
    title: "Schedules",
    icon: "fas fa-clipboard-list"
  },
  {
    link: "/departments",
    title: "Departments",
    icon: "fas fa-building"
  },
  {
    link: "/majors",
    title: "Majors",
    icon: "fas fa-book-open"
  },
  {
    link: "/classes",
    title: "Classes",
    icon: "fas fa-graduation-cap"
  },
  {
    link: "/teachers",
    title: "Teachers",
    icon: "fas fa-chalkboard-teacher"
  },
  {
    link: "/students",
    title: "Students",
    icon: "fa-solid fa-book-open-reader"
   
    
  },
  
  
  {
    link: "/classrooms",
    title: "Rooms",
    icon: "fas fa-clipboard"
  }
];

sidebarProfItems = [
  {
    link: "/home",
    title: "Home",
    icon: "fas fa-home"
  },
  {
    link: "/schedules",
    title: "Schedules",
    icon: "fas fa-clipboard-list"
  },
  {
    link: "/notAvailables",
    title: "Not Availables",
    icon: "fas fa-clipboard-list"
  },

]
  constructor(private cookieService: CookieService) { }

  handleChangeBars(index: number): void {
  this.active = index;
}
  ngOnInit(): void {

    if(this.cookieService.get('role') == "Admin"){
     this.sidebarItems= this.sidebarAdminItems;
    }else {
     this.sidebarItems= this.sidebarProfItems;
    }

}
}