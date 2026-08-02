import { HttpHandlerFn, HttpInterceptorFn, HttpRequest } from '@angular/common/http';
import { Authentication } from './authService';
import { inject } from '@angular/core';

export const authInterceptor = (req: HttpRequest<unknown>, next: HttpHandlerFn) => {
  const authToken = inject(Authentication).getAuthToken();
  const newReq = req.clone({
    headers: req.headers.append('X-Authentication-Token', authToken),
  });
  console.log('authInterceptor called with token:', newReq);
  return next(newReq);
};
