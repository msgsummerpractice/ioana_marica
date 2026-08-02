import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { toSignal } from '@angular/core/rxjs-interop';
import { DogService } from './models/dogService.interface';

@Injectable({
  providedIn: 'root',
})
export class Service {
  constructor(private http: HttpClient) {}

  dogs = signal<string[]>([]);

  async fetchDogs() {
    const response = await fetch('https://dog.ceo/api/breeds/image/random/3');
    const dog: DogService = await response.json();
    this.dogs.set(dog.message);
  }
}
