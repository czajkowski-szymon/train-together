import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { FriendInvite } from '../../interfaces/friend-invite.interface';
import { Friendship } from '../../interfaces/freindship.interface';

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

  acceptFriendInvite(inviteId: number): Observable<Friendship> {
    return this.http.patch<Friendship>(this.friendshipApiUrl + '/invitations/' + inviteId + '/accept', null);
  }

  denyFriendInvite(inviteId: number): Observable<Friendship> {
    return this.http.patch<Friendship>(this.friendshipApiUrl + '/invitations/' + inviteId + '/decline', null);
  }
}
