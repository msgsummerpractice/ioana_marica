import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { forkJoin, Observable } from 'rxjs';

export interface Dog {
  message: string;
  status: string;
}

@Injectable({
  providedIn: 'root',
})
export class Service {
  constructor(private http: HttpClient) {}

  fetchDogs(): Observable<Dog[]> {
    return forkJoin([
      this.http.get<Dog>('https://dog.ceo/api/breeds/image/random'),
      this.http.get<Dog>('https://dog.ceo/api/breeds/image/random'),
      this.http.get<Dog>('https://dog.ceo/api/breeds/image/random'),
    ]);
  }
}
