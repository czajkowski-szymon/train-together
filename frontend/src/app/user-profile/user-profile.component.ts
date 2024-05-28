import { Component, OnInit, inject } from '@angular/core';
import { UserService } from '../services/user/user.service';
import { User } from '../interfaces/user.interface';
import { ActivatedRoute } from '@angular/router';
import { NavBarComponent } from '../nav-bar/nav-bar.component';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [NavBarComponent],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.scss'
})
export class UserProfileComponent implements OnInit {
  userService: UserService = inject(UserService);
  route: ActivatedRoute = inject(ActivatedRoute);
  username: string | null = "";
  user?: User;

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.username = params.get('username') || "";
      this.userService.getUserByUsername(this.username).subscribe(response => {
        this.user = response
      })
    });
  }
}
