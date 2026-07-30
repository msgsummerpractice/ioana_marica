import { TestBed } from '@angular/core/testing';

import { Authentification } from './authService';

describe('Authentification', () => {
  let service: Authentification;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Authentification);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
