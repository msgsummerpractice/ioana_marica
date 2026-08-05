import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class Authentication {
  private http = inject(HttpClient);

  isAuthenticated = signal(!!localStorage.getItem('token'));

  login(email: string, password: string) {
    return this.http
      .post<any>('http://localhost:8080/api/auth/login', {
        email,
        password,
      })
      .pipe(
        tap((response) => {
          localStorage.setItem('token', response.token);

          this.isAuthenticated.set(true);
        }),
      );
  }

  register(user: any) {
    return this.http.post<any>('http://localhost:8080/api/auth/register', user);
  }

  logout() {
    this.isAuthenticated.set(false);
    localStorage.removeItem('token');
  }
}
