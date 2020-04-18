import { TestBed } from '@angular/core/testing';

import { ClinicAdminService } from './clinic-admin.service';

describe('ClinicAdminService', () => {
  let service: ClinicAdminService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ClinicAdminService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
