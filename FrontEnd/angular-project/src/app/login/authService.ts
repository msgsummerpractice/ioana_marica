import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class Authentication {
  isAuthenticated = signal(false);

  login() {
    this.isAuthenticated.set(true);
  }

  logout() {
    this.isAuthenticated.set(false);
  }

  getAuthToken() {
    return 'your-auth-token';
  }
}
