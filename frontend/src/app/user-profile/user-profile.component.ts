import { Component, OnInit, inject } from '@angular/core';
import { UserService } from '../services/user/user.service';
import { User } from '../interfaces/user.interface';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import { FriendshipService } from '../services/friendship/friendship.service';
import { AuthService } from '../services/auth/auth.service';
import { Observable, catchError, of } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { AbstractControl, FormBuilder, ReactiveFormsModule, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { TrainingService } from '../services/training/training.service';
import { Sport } from '../interfaces/sport.interface';
import { AdminService } from '../services/admin/admin.service';
import { MobileFooterComponent } from '../mobile-footer/mobile-footer.component';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [NavBarComponent, ReactiveFormsModule, MobileFooterComponent],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.scss'
})
export class UserProfileComponent implements OnInit {
  fb: FormBuilder = inject(FormBuilder);
  authService: AuthService = inject(AuthService);
  userService: UserService = inject(UserService);
  adminService: AdminService = inject(AdminService);
  router: Router = inject(Router);
  friendshipService: FriendshipService = inject(FriendshipService);
  trainingService: TrainingService = inject(TrainingService); 
  route: ActivatedRoute = inject(ActivatedRoute);
  username: string | null = "";
  user?: User;
  message?: string = "";
  trainingMessage: string = "";
  isFriend?: boolean;
  isLoggedUserAdmin!: boolean;
  sports: Sport[] = [];
  submitted = false;
  trainingInviteForm = this.fb.group({
    sport: ['', Validators.required],
    date: ['', [Validators.required, this.futureDateValidator()]],
    message: ['', Validators.required]
  });

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.username = params.get('username') || "";
      this.userService.getUserByUsername(this.username).subscribe(response => {
        this.isLoggedUserAdmin = this.authService.currentUserSignal()?.role === 'ADMIN'
        this.user = response
        this.userService.isUserFriend(this.user?.userId).subscribe(response => {
          this.getUsersSports();
          this.isFriend = response;
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

  getUsersSports(): void {
    this.userService.getUsersSports(this.user?.userId).subscribe(sports => {
      this.sports = sports;
    });
  }

  deleteUserAccount(): void {
    this.adminService.deleteUserAccount(this.user?.userId).subscribe(_ => {
      this.router.navigateByUrl('/discover');
    });
  }

  blockUserAccount(): void {
    this.adminService.blockUserAccount(this.user?.userId).subscribe(_ => {
      this.router.navigateByUrl('/discover');
    });
  }

  futureDateValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!control.value) {
        return null;
      }
  
      const selectedDate = new Date(control.value);
      const today = new Date();
      
      // Zerowanie czasu w dzisiejszej dacie
      today.setHours(0, 0, 0, 0);
  
      return selectedDate > today ? null : { futureDate: { required: today, actual: selectedDate } };
    };
  }

  onSubmit(): void {
    if (this.trainingInviteForm.valid) {
      const loggedUserId = this.authService.currentUserSignal()?.userId;
      const date = this.trainingInviteForm.get('date')?.value;
      const sport = this.trainingInviteForm.get('sport')?.value;
      const message = this.trainingInviteForm.get('message')?.value;
      const senderId = loggedUserId;
      const receiverId = this.user?.userId;
  
      this.trainingService.sendTrainingInvite({
        date: new Date(date!),
        sport: sport!,
        message: message!,
        senderId: senderId!,
        receiverId: receiverId!
      }).subscribe(_ => {
        this.trainingMessage = 'Invitation sent succesfully';
      });
    } else {
      console.log('not valid');
    }
  }
}
