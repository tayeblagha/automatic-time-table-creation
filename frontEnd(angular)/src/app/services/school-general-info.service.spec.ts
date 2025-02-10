import { TestBed } from '@angular/core/testing';

import { SchoolGeneralInfoService } from './school-general-info.service';

describe('SchoolGeneralInfoService', () => {
  let service: SchoolGeneralInfoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SchoolGeneralInfoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
