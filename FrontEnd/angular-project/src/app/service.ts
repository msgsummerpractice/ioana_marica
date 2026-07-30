import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { forkJoin } from 'rxjs';

export interface Dog {
  message: string;
  status: string;
}

export class Service {
  constructor(private http: HttpClient) {}

  dogs = signal<Dog[]>([]);

  fetchDogs(): void {
    forkJoin([
      this.http.get<Dog>('https://dog.ceo/api/breeds/image/random'),
      this.http.get<Dog>('https://dog.ceo/api/breeds/image/random'),
      this.http.get<Dog>('https://dog.ceo/api/breeds/image/random'),
    ]).subscribe({
      next: (dogs) => this.dogs.set(dogs),
      error: (err) => console.error(err),
    });
  }
}
