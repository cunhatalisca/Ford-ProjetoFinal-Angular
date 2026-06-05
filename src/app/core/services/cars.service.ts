import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cars } from '../models/cars.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CarsService {
  constructor() {}

  private readonly apiUrl = `${environment.apiUrl}/cars`;
  private http = inject(HttpClient);

  getCars(): Observable<Cars[]> {
    return this.http.get<Cars[]>(this.apiUrl);
  }

  getCar(id: number | string): Observable<Cars> {
    return this.http.get<Cars>(`${this.apiUrl}/${id}`);
  }

  getFeaturedCars(): Observable<Cars[]> {
    return this.http.get<Cars[]>(`${this.apiUrl}/featured`);
  }

  getLastVehicles(): Observable<Cars[]> {
    return this.http.get<Cars[]>(`${this.apiUrl}/latest`);
  }

  addCar(car: Omit<Cars, 'id'>): Observable<Cars> {
    return this.http.post<Cars>(this.apiUrl, car);
  }

  updateCar(car: Cars): Observable<Cars> {
    return this.http.put<Cars>(`${this.apiUrl}/${car.id}`, car);
  }

  deleteCar(id: number | string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
