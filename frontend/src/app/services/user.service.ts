import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../interfaces/user.interface';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private usersApiUrl = 'http://localhost:8080/api/v1/users';
  private http = inject(HttpClient);

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.usersApiUrl);
  }
}