import { HttpClient } from '@angular/common/http';
import { Injectable, inject, signal } from '@angular/core';
import { LoginRequest } from '../../interfaces/login-request.interface';
import { BehaviorSubject, Observable } from 'rxjs';
import { LoginResponse } from '../../interfaces/login-response.interface';
import { User } from '../../interfaces/user.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private authApiUrl = "http://localhost:8080/api/v1/auth";
  http: HttpClient = inject(HttpClient);
  currentUserSignal = signal<User | undefined | null>(undefined);

  login(request: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.authApiUrl + '/login', request);
  }

  authenticate(): Observable<User> {
    return this.http.get<User>(this.authApiUrl);
  }

  isAuthenticated(): boolean {
    if (localStorage.getItem('token')?.length! > 0) {
      return true;
    }
    return false;
  }
}