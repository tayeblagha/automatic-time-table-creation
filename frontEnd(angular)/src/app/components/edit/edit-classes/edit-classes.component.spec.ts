import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditClassesComponent } from './edit-classes.component'; 

describe('EditClasseComponent', () => {
  let component: EditClassesComponent;
  let fixture: ComponentFixture<EditClassesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EditClassesComponent]
    });
    fixture = TestBed.createComponent(EditClassesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
