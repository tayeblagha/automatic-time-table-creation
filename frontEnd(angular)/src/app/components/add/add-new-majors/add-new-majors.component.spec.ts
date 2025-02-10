import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddNewMajorComponent } from './add-new-majors.component';

describe('AddNewMajorComponent', () => {
  let component: AddNewMajorComponent;
  let fixture: ComponentFixture<AddNewMajorComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddNewMajorComponent]
    });
    fixture = TestBed.createComponent(AddNewMajorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
