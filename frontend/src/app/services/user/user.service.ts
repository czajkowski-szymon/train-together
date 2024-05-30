import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../../interfaces/user.interface';
import { RegistrationRequest } from '../../interfaces/registration-request.interface';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private usersApiUrl = 'http://localhost:8080/api/v1/users';
  private http = inject(HttpClient);

  register(request: RegistrationRequest): Observable<User> {
    return this.http.post<User>(this.usersApiUrl + '/register', request)
  }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.usersApiUrl);
  }

  getUsersByCity(city: string): Observable<User[]> {
    return this.http.get<User[]>(this.usersApiUrl + '/city?cityname=' + city);
  }

  getUserByUsername(username: string): Observable<User> {
    return this.http.get<User>(this.usersApiUrl + '/' + username);
  }

  isUserFriend(userId: number | undefined): Observable<boolean> {
    return this.http.get<boolean>(this.usersApiUrl + '/isfriend/' + userId);
  }
}