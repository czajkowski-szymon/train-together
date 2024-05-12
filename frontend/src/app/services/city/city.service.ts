import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { City } from '../../interfaces/city.interface';

@Injectable({
  providedIn: 'root'
})
export class CityService {
  private cityApiUrl = 'http://localhost:8080/api/v1/cities'
  http: HttpClient = inject(HttpClient);

  getCities(): Observable<City[]> {
    return this.http.get<City[]>(this.cityApiUrl);
  }
}
