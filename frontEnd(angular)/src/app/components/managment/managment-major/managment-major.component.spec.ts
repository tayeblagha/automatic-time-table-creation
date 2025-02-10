import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagmentMajorComponenet } from './managment-major.component';

describe('GestionFiliereComponent', () => {
  let component: ManagmentMajorComponenet;
  let fixture: ComponentFixture<ManagmentMajorComponenet>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManagmentMajorComponenet ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManagmentMajorComponenet);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
