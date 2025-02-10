import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Room } from 'src/app/models/rooms.models';
import { RoomService } from 'src/app/services/classroom.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-managment-room',
  templateUrl: './managment-rooms.component.html',
  styleUrls: ['./managment-rooms.component.css']
})
export class ManagmentRoomsComponent implements OnInit {
  keyword=""
  allTypeRooms=["Amphi","ComputerLab","ElectricityLab","Workshop","Nano"]
  typeRoom=""
  rooms: Room[] = [];
  errorMessage: string = '';
  searchFormGroup!: FormGroup;
  page: number = 0;
  size: number = 6;
  totalPages: number = 0;
  currentPage: number = 0;
  totalElements: number = 0;
  displayedPages: number[] = [];
  dropdownSettings = {
    singleSelection: true, // Allow multiple selection
    
    selectAllText: 'Select All',
    unSelectAllText: 'Unselect All',
    itemsShowLimit: 3,
    allowSearchFilter: true, // Enable search functionality
  };
  constructor(
    private roomService: RoomService,
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.searchFormGroup = this.fb.group({
      keyword: this.fb.control(''),
    });
    this.handleSearchRooms();
  }



  handleEditRoom(room: Room) {
    this.router.navigateByUrl('/classrooms/edit/'+room.id, { state: room });
  }

  handleDeleteRoom(room: Room) {
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
        this.roomService.deleteRoom(room.id).subscribe();;
        this.rooms.splice(this.rooms.indexOf(room), 1);
      }
    });
  }

  handleChangeSize(event: Event) {
    this.size = parseInt((event.target as HTMLInputElement).value);
    this.handleSearchRooms();
  }

  handleSearchRooms() {
   
    const keyword = this.keyword;
    
    
    this.roomService.searchRooms(keyword,this.typeRoom ,this.page, this.size).subscribe({
      next: (data) => {
        this.rooms = data.content;
        console.log(data)
        this.totalPages = data.totalPages;
        this.currentPage = data.number;
        this.totalElements = data.totalElements;
        this.setDisplayedPages();
      },
      error: (err) => {
        this.errorMessage = err;
        console.log(err);
      }
    });
  } 

  setDisplayedPages() {
    this.displayedPages = [];
    const startPage = Math.floor(this.currentPage / 3) * 3;
    for (let i = startPage; i < startPage + 3 && i < this.totalPages; i++) {
      this.displayedPages.push(i);
    }
  }

  goToPage(page: number) {
    this.currentPage = page;
    this.page = page;
    this.handleSearchRooms();
  }

  goToPreviousSet() {
    const startPage = Math.floor(this.currentPage / 3) * 3;
    if (startPage - 3 >= 0) {
      this.currentPage = startPage - 3;
      this.page = this.currentPage;
      this.handleSearchRooms();
    }
  }

  goToNextSet() {
    const startPage = Math.floor(this.currentPage / 3) * 3;
    if (startPage + 3 < this.totalPages) {
      this.currentPage = startPage + 3;
      this.page = this.currentPage;
      this.handleSearchRooms();
    }
  }

  onDepartmentSelect(event:any){
    this.page=0
    this.currentPage=0
   this.typeRoom=event 
   console.log(this.typeRoom)
   this.handleSearchRooms();
  }
  onDepartmentDeselect(){
    this.page=0
    this.currentPage=0
    this.typeRoom=""
    this.handleSearchRooms();
  }

  handleLabel(){
    this.page=0
    this.currentPage=0
    this.handleSearchRooms();
  }
}
