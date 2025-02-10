import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Room } from 'src/app/models/rooms.models';
import { RoomService } from 'src/app/services/classroom.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-new-room',
  templateUrl: './add-new-room.component.html',
  styleUrls: ['./add-new-room.component.css'],
})
export class AddNewRoomComponent implements OnInit {
  roomData: Partial<Room> = {
    typeRoom: '',
    
  };
  room_id:any

  constructor(private roomService: RoomService, 
    private router: Router,
    private activatedRoute:ActivatedRoute,
  ) {}
  ngOnInit(): void {
    this.room_id= this.activatedRoute.snapshot.params["id"]
    if (this.room_id){
      this.getRoomById()
    }
  }

  handleAddRoom(form: any) {
    if (form.valid) {
      this.roomService.saveRoom(this.roomData).subscribe({
        next: (data) => {
          Swal.fire('Success', 'Room added successfully', 'success');
          this.router.navigateByUrl('/classrooms');
        },
        error: (err) => {
          console.log(err);
        },
      });
    } else {
      Swal.fire('Error', 'Please fill in all fields of the form correctly', 'error');
    }
  }

  getRoomById(){
    this.roomService.getRoom(this.room_id).subscribe(
      data=>{
        this.roomData=data
      }
    )
  }


}
