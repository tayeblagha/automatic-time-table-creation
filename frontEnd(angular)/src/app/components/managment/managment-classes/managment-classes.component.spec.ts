import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagmentClassesComponent } from './managment-classes.component';

describe('GestionClasseComponent', () => {
  let component: ManagmentClassesComponent;
  let fixture: ComponentFixture<ManagmentClassesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManagmentClassesComponent]
    });
    fixture = TestBed.createComponent(ManagmentClassesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
