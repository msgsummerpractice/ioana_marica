import { Component, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { Service } from './service';
import { Authentification } from './login/authentification';
import { Auth } from './login/auth';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, MatButtonModule, MatToolbarModule, MatIconModule, Auth],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
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
