import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagmentTeacherComponent } from './managment-prof.component';

describe('GestionProfComponent', () => {
  let component: ManagmentTeacherComponent;
  let fixture: ComponentFixture<ManagmentTeacherComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManagmentTeacherComponent]
    });
    fixture = TestBed.createComponent(ManagmentTeacherComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
