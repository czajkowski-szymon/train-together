import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Sport } from '../../interfaces/sport.interface';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SportService {
  sportsApiUrl = 'http://localhost:8080/api/v1/sports';
  http: HttpClient = inject(HttpClient);

  getSports(): Observable<Sport[]> {
    return this.http.get<Sport[]>(this.sportsApiUrl);
  }
}
