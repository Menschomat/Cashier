import { TestBed } from '@angular/core/testing';

import { StatusServiceService } from './status-service.service';

describe('StatusServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: StatusServiceService = TestBed.get(StatusServiceService);
    expect(service).toBeTruthy();
  });
});
