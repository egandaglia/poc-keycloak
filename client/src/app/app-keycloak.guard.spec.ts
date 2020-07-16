import { TestBed } from '@angular/core/testing';

import { AppKeycloakGuard } from './app-keycloak.guard';

describe('AppKeycloakGuard', () => {
  let guard: AppKeycloakGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(AppKeycloakGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
