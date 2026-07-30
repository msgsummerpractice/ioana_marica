import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { Auth } from '../login/authDirective';
import { Authentification } from '../login/authService';
import { Service } from '../service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [MatButtonModule, MatToolbarModule, MatIconModule, Auth, RouterLink, CommonModule],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  private dogService = inject(Service);
  private authentification = inject(Authentification);

  dogs = this.dogService.dogs;

  constructor() {
    this.dogService.fetchDogs();
  }

  login() {
    this.authentification.login();
  }

  logout() {
    this.authentification.logout();
  }
}
