import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotAvaliablityComponent } from './not-avaliablity.component';

describe('NonDisponibleComponent', () => {
  let component: NotAvaliablityComponent;
  let fixture: ComponentFixture<NotAvaliablityComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NotAvaliablityComponent]
    });
    fixture = TestBed.createComponent(NotAvaliablityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
