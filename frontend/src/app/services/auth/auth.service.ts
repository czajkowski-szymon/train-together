import { HttpClient } from '@angular/common/http';
import { Injectable, inject, signal } from '@angular/core';
import { LoginRequest } from '../../interfaces/login-request.interface';
import { Observable } from 'rxjs';
import { LoginResponse } from '../../interfaces/login-response.interface';
import { User } from '../../interfaces/user.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private authApiUrl = "http://localhost:8080/api/v1/auth";
  private authenticated = false;
  http: HttpClient = inject(HttpClient);
  currentUserSignal = signal<User | undefined | null>(undefined);

  login(request: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.authApiUrl + '/login', request)
  }

  setAuthenticated(authenticated: boolean): void {
    this.authenticated = authenticated;
  }

  isAuthenticated(): boolean {
    return this.authenticated;
  }
}