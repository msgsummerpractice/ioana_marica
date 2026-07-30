import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class Authentification {
  isAuthentificated = signal(false);

  login() {
    this.isAuthentificated.set(true);
  }

  logout() {
    this.isAuthentificated.set(false);
  }

  getAuthToken() {
    return 'your-auth-token';
  }
}
