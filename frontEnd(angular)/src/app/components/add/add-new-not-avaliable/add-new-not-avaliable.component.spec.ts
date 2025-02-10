import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddNewNotAvaliableComponent } from './add-new-not-avaliable.component';

describe('AddNewNotAvaliableComponent', () => {
  let component: AddNewNotAvaliableComponent;
  let fixture: ComponentFixture<AddNewNotAvaliableComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddNewNotAvaliableComponent]
    });
    fixture = TestBed.createComponent(AddNewNotAvaliableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
