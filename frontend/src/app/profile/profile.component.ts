import { Component, OnInit, inject } from '@angular/core';
import { User } from '../interfaces/user.interface';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import { AuthService } from '../services/auth/auth.service';
import { Training } from '../interfaces/training.interface';
import { TrainingService } from '../services/training/training.service';
import { DatePipe } from '@angular/common';
import { FriendInvite } from '../interfaces/friend-invite.interface';
import { FriendshipService } from '../services/friendship/friendship.service';
import { TrainingInvite } from '../interfaces/training-invite.interface';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [NavBarComponent, DatePipe, RouterLink],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss',
})
export class ProfileComponent implements OnInit {
  authService: AuthService = inject(AuthService);
  trainingService: TrainingService = inject(TrainingService);
  freindshipService: FriendshipService = inject(FriendshipService);
  router: Router = inject(Router);
  username: string | undefined = '';
  user?: User | null | undefined;
  upcomingTrainings?: Array<Training> | null | undefined;
  pastTrainings?: Array<Training> | null | undefined;
  trainingInvites?: Array<TrainingInvite> | null | undefined;
  friendInvites?: Array<FriendInvite> | null | undefined;
  friendsList?: Array<User> | null | undefined;

  ngOnInit(): void {
    this.authService.authenticate().subscribe((response) => {
      this.authService.currentUserSignal.set(response);
      this.user = response;
    });

    this.trainingService.getUpcomingTrainings().subscribe((response) => {
      this.upcomingTrainings = response;
    });

    this.trainingService.getPastTrainings().subscribe((response) => {
      this.pastTrainings = response;
    });

    this.trainingService.getTrainingInvites().subscribe((response) => {
      console.log(this.user?.userId);
      this.trainingInvites = response;
    });

    this.freindshipService.getAllFriendInvites().subscribe((response) => {
      this.friendInvites = response;
    });

    this.freindshipService.getFriends().subscribe(response => {
      this.friendsList = response;
    });
  }

  acceptTrainingInvite(inviteId: number) {
    if (inviteId) {
      this.trainingService.acceptTrainingInvite(inviteId).subscribe();
    }
    window.location.reload();
  }

  denyTrainingInvite(inviteId: number) {
    if (inviteId) {
      this.trainingService.denyTrainingInvite(inviteId).subscribe();
    }
    window.location.reload();
  }

  acceptFriendInvite(inviteId: number) {
    if (inviteId) {
      this.freindshipService.acceptFriendInvite(inviteId).subscribe();
    }
    window.location.reload();
  }

  denyFriendInvite(inviteId: number) {
    if (inviteId) {
      this.freindshipService.denyFriendInvite(inviteId).subscribe();
    }
    window.location.reload();
  }
}
