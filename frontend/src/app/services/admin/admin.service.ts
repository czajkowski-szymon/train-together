import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private adminApiUrl = 'http://localhost:8080/api/v1/admin/users';
  http: HttpClient = inject(HttpClient);

  deleteUserAccount(userId: number | undefined): Observable<void> {
    return this.http.delete<void>(this.adminApiUrl + '/' + userId);
  }

  blockUserAccount(userId: number | undefined): Observable<void> {
    return this.http.patch<void>(this.adminApiUrl + '/block/' + userId, null);
  }
}
