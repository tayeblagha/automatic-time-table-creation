import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TimetableDisplayerComponent } from './timetable-displayer.component';

describe('TimetableDisplayerComponent', () => {
  let component: TimetableDisplayerComponent;
  let fixture: ComponentFixture<TimetableDisplayerComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TimetableDisplayerComponent]
    });
    fixture = TestBed.createComponent(TimetableDisplayerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
