import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from '../interfaces/user.interface';
import { UserService } from '../services/user/user.service';
import { NavBarComponent } from '../nav-bar/nav-bar.component';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [NavBarComponent],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit {
  route: ActivatedRoute = inject(ActivatedRoute);
  username: string = '';
  user?: User = undefined;
  userString = '';
  userService:  UserService = inject(UserService);

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.username = params['username'];
    })
    this.userService.getUserByUsername(this.username).subscribe(user => {
      this.user = user;
      this.userString = JSON.stringify(this.user);
    });

  }
}
