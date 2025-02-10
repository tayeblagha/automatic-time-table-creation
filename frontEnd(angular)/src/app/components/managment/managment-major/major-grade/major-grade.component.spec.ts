import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MajorGradeComponent } from './major-grade.component';

describe('MajorGradeComponent', () => {
  let component: MajorGradeComponent;
  let fixture: ComponentFixture<MajorGradeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MajorGradeComponent]
    });
    fixture = TestBed.createComponent(MajorGradeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
