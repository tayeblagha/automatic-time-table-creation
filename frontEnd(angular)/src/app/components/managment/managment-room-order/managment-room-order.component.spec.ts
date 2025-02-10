import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagmentRoomOrderComponent } from './managment-room-order.component';

describe('ManagmentRoomOrderComponent', () => {
  let component: ManagmentRoomOrderComponent;
  let fixture: ComponentFixture<ManagmentRoomOrderComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManagmentRoomOrderComponent]
    });
    fixture = TestBed.createComponent(ManagmentRoomOrderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
