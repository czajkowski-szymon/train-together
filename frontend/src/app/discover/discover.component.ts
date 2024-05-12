import { Component, OnInit, inject } from '@angular/core';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import { NgFor } from '@angular/common';
import { ProfileCardComponent } from './profile-card/profile-card.component';
import { UserService } from '../services/user.service';
import { User } from '../interfaces/user.interface';

@Component({
  selector: 'app-discover',
  standalone: true,
  imports: [NavBarComponent, NgFor, ProfileCardComponent],
  templateUrl: './discover.component.html',
  styleUrl: './discover.component.scss'
})
export class DiscoverComponent implements OnInit {
  private userService = inject(UserService);
  users?: User[];

  ngOnInit(): void {
    this.userService.getUsers().subscribe(users => {
      this.users = users;
    });
  }
}