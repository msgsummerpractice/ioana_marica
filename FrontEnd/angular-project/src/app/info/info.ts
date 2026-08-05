import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { Authentication } from '../login/authService';

@Component({
  selector: 'app-info',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './info.html',
})
export class Info {
  private auth = inject(Authentication);
  private router = inject(Router);

  logout(): void {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}
