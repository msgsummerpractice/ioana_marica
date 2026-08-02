import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { AuthDirective } from '../login/authDirective';
import { Authentication } from '../login/authService';
import { Service } from '../dogService';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    MatButtonModule,
    MatToolbarModule,
    MatIconModule,
    AuthDirective,
    RouterLink,
    CommonModule,
  ],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  private dogService = inject(Service);
  private authentification = inject(Authentication);

  dogs = this.dogService.dogs;

  constructor() {
    this.dogService.fetchDogs();
  }

  logout() {
    this.authentification.logout();
  }
}
