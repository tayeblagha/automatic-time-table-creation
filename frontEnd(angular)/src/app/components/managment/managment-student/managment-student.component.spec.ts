import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagmentStudentComponent } from './managment-student.component';

describe('ManagmentStudentComponent', () => {
  let component: ManagmentStudentComponent;
  let fixture: ComponentFixture<ManagmentStudentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManagmentStudentComponent]
    });
    fixture = TestBed.createComponent(ManagmentStudentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
