import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SchoolGeneralInfoComponent } from './school-general-info.component';

describe('SchoolGeneralInfoComponent', () => {
  let component: SchoolGeneralInfoComponent;
  let fixture: ComponentFixture<SchoolGeneralInfoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SchoolGeneralInfoComponent]
    });
    fixture = TestBed.createComponent(SchoolGeneralInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
