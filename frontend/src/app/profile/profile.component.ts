import { Component, OnInit, inject } from '@angular/core';
import { User } from '../interfaces/user.interface';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import { AuthService } from '../services/auth/auth.service';
import { Training } from '../interfaces/training.interface';
import { TrainingService } from '../services/training/training.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [NavBarComponent, DatePipe],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit {
  authService: AuthService = inject(AuthService);
  trainingService: TrainingService = inject(TrainingService)
  username: string | undefined = '';
  user?: User | null | undefined = undefined;
  upcomingTrainings?: Array<Training> | null | undefined;
  pastTrainings?: Array<Training> | null | undefined;

  ngOnInit(): void {
    this.authService.authenticate().subscribe(response => {
      this.authService.currentUserSignal.set(response);
      this.user = response;
    });

    this.trainingService.getUpcomingTrainings().subscribe(response => {
      this.upcomingTrainings = response;
    });

    this.trainingService.getPastTrainings().subscribe(response => {
      this.pastTrainings = response;
    });
  }
}