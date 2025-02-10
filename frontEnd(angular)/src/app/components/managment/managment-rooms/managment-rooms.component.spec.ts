import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagmentRoomsComponent } from './managment-rooms.component';

describe('GestionSallesComponent', () => {
  let component: ManagmentRoomsComponent;
  let fixture: ComponentFixture<ManagmentRoomsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManagmentRoomsComponent]
    });
    fixture = TestBed.createComponent(ManagmentRoomsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
