import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';

export interface Dog {
  message: string[];
  status: string;
}

@Injectable({
  providedIn: 'root',
})
export class Service {
  constructor(private http: HttpClient) {}

  dogs = signal<string[]>([]);

  async fetchDogs() {
    const response = await fetch('https://dog.ceo/api/breeds/image/random/3');
    const dog: Dog = await response.json();
    this.dogs.set(dog.message);
  }
}
