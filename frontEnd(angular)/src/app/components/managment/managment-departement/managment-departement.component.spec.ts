import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagmentDepartmentComponent } from './managment-departement.component';

describe('GestionDepartementComponent', () => {
  let component: ManagmentDepartmentComponent;
  let fixture: ComponentFixture<ManagmentDepartmentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManagmentDepartmentComponent]
    });
    fixture = TestBed.createComponent(ManagmentDepartmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
