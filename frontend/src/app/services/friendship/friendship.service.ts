import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { FriendInvite } from '../../interfaces/friend-invite.interface';

@Injectable({
  providedIn: 'root'
})
export class FriendshipService {
  private friendshipApiUrl = 'http://localhost:8080/api/v1/friendships';
  private http = inject(HttpClient);

  getAllFriendInvites(): Observable<Array<FriendInvite>> {
    return this.http.get<Array<FriendInvite>>(this.friendshipApiUrl + '/invitations/received');
  }

  sendFriendInvite(request: {senderId: number, receiverId: number}): Observable<FriendInvite> {
    return this.http.post<FriendInvite>(this.friendshipApiUrl + '/invitations', request);
  }
}
