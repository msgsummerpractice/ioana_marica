import { Component, signal, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MatButton } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { forkJoin, Observable } from 'rxjs';

interface Dog {
  message: string;
  status: string;
}

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, MatButton, MatToolbarModule, MatIconModule],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App implements OnInit {
  protected readonly title = signal('my-app');

  dogs$!: Observable<Dog[]>;

  constructor(private http: HttpClient) {}

  fetchDog() {
    this.dogs$ = forkJoin([
      this.http.get<Dog>('https://dog.ceo/api/breeds/image/random'),
      this.http.get<Dog>('https://dog.ceo/api/breeds/image/random'),
      this.http.get<Dog>('https://dog.ceo/api/breeds/image/random'),
    ]);
  }

  ngOnInit(): void {
    this.fetchDog();
  }
}
