import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { Authentication } from '../login/authService';

export const loginGuard: CanActivateFn = (route, state) => {
  const auth = inject(Authentication);
  const router = inject(Router);

  if (auth.isAuthenticated()) {
    router.navigateByUrl('/');
    return false;
  }
  return true;
};
