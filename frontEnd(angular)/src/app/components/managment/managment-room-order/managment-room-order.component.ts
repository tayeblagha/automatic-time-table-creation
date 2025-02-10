import { Component, OnInit, Renderer2 } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Classes } from 'src/app/models/classes.models';
import { RoomOrder } from 'src/app/models/roomOrder.models';
import { ClassesService } from 'src/app/services/classe.service';
import { RoomOrderService } from 'src/app/services/roomoder.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-managment-room-order',
  templateUrl: './managment-room-order.component.html',
  styleUrls: ['./managment-room-order.component.css']
})
export class ManagmentRoomOrderComponent implements OnInit {
  class_id:any
  roomOrders:RoomOrder[]=[]
  newClass:Partial<Classes>|undefined
  constructor(
  
    private activatedRoute:ActivatedRoute,
    private roomOrderService:RoomOrderService,
    private router:Router,
    private clService:ClassesService,

  
  ){}
  ngOnInit(): void {
    this.class_id= this.activatedRoute.snapshot.params["classe_id"]
    if (this.class_id){
      this.getRoomsByClassId()
      this.getClass()
    }
    
  }
  getClass(){
    this.clService.getClasse(this.class_id).subscribe(
      data=>{
        this.newClass=data
      }
      
    )
  }

  getRoomsByClassId(){
    this.roomOrderService.getRoomOrdersByClassId(this.class_id).subscribe(
      data=>{
        console.log(data)
        this.roomOrders=data
        console.log(this.roomOrders)
      }
    )
  }


  moveUp(index:number){
    if (index > 0) {
      [this.roomOrders[index], this.roomOrders[index - 1]] = [
        this.roomOrders[index - 1],
        this.roomOrders[index],
      ];
    }
  }

  moveDown(index:number){
    if (index < this.roomOrders.length - 1) {
      [this.roomOrders[index], this.roomOrders[index + 1]] = [
        this.roomOrders[index + 1],
        this.roomOrders[index],
      ];
    }
  }
  
  saveOrder(){
    this.roomOrders.forEach((item, i) => (item.orderIndex = i));
    this.roomOrderService.updateRoomOrders(this.roomOrders).subscribe(() => {
      Swal.fire('Succes', 'Order changed successfully', 'success');
      
      console.log('Order updated');
    });
  }
  

}
