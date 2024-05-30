import { Component, OnInit, inject } from '@angular/core';
import { UserService } from '../services/user/user.service';
import { User } from '../interfaces/user.interface';
import { ActivatedRoute } from '@angular/router';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import { FriendshipService } from '../services/friendship/friendship.service';
import { AuthService } from '../services/auth/auth.service';
import { catchError, of } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { TrainingService } from '../services/training/training.service';
import { TrainingInviteRequest } from '../interfaces/training-invite-request.interface';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [NavBarComponent, ReactiveFormsModule],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.scss'
})
export class UserProfileComponent implements OnInit {
  fb: FormBuilder = inject(FormBuilder);
  authService: AuthService = inject(AuthService);
  userService: UserService = inject(UserService);
  friendshipService: FriendshipService = inject(FriendshipService);
  trainingService: TrainingService = inject(TrainingService); 
  route: ActivatedRoute = inject(ActivatedRoute);
  username: string | null = "";
  user?: User;
  message?: string = "";
  isFriend?: boolean;
  trainingInviteForm = this.fb.group({
    sport: ['', Validators.required],
    date: ['', Validators.required],
    message: ['', Validators.required]
  });

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.username = params.get('username') || "";
      this.userService.getUserByUsername(this.username).subscribe(response => {
        this.user = response
        this.userService.isUserFriend(this.user?.userId).subscribe(response => {
          this.isFriend = response;
          console.log(this.isFriend);
        });
      })
    });
  }

  sendFriendInvite(): void {
    const loggedUserId = this.authService.currentUserSignal()?.userId;
    if (loggedUserId && this.user?.userId) {
      this.friendshipService.sendFriendInvite({
        senderId: loggedUserId,
        receiverId: this.user?.userId  
      }).pipe(
        catchError((error: HttpErrorResponse) => {
          if (error.status === 409) {
            this.message = 'Invitation already sent';
          }
          return of(null)
        })
      ).subscribe(response => {
        if (response) {
          this.message = 'Invitation sent successfully';
        }
      })
    }
  }

  onSubmit(): void {
    const loggedUserId = this.authService.currentUserSignal()?.userId;
    const date = this.trainingInviteForm.get('date')?.value;
    const sport = this.trainingInviteForm.get('sport')?.value;
    const message = this.trainingInviteForm.get('message')?.value;
    const senderId = loggedUserId;
    const receiverId = this.user?.userId;

    console.log({ date, sport, message, senderId, receiverId });

    this.trainingService.sendTrainingInvite({
      date: new Date(date!),
      sport: sport!,
      message: message!,
      senderId: senderId!,
      receiverId: receiverId!
    }).subscribe(response => {
      console.log(response);
    });
  }
}
