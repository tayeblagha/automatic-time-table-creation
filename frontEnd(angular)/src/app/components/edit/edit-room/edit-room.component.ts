import { FormBuilder, FormGroup } from '@angular/forms';
import { Room } from '../../../models/rooms.models';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { RoomService } from 'src/app/services/classroom.service';

@Component({
  selector: 'app-edit-room',
  templateUrl: './edit-room.component.html',
  styleUrls: ['./edit-room.component.css']
})
export class EditRoomComponent {
 editRoomFormGroup!: FormGroup;
  room!: Room;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private roomService: RoomService
  ) {
    this.room = this.router.getCurrentNavigation()?.extras.state as Room;
  }

  ngOnInit(): void {
    this.editRoomFormGroup = this.fb.group({
      capacity: [''],
      numRoom: [''],
      typeRoom: [''],
    });
    this.setFormValues();
  }
  setFormValues() {
    if (this.room) {
      this.editRoomFormGroup.patchValue({
        capacity: this.room.capacity,
        numRoom: this.room.numRoom,
        typeRoom: this.room.typeRoom,
      });
    }
  }


  handleUpdateRoom() {

    if (this.editRoomFormGroup.valid) {
      this.roomService
        .updateRoom(this.room.id, this.editRoomFormGroup.value)
        .subscribe((data) => {
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: 'Room updated successfully',
            showConfirmButton: false,
            timer: 1500,
          });
          this.router.navigateByUrl('/classrooms');
        });
    }
    }
  
}
