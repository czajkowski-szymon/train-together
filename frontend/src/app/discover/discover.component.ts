import { Component, OnInit, inject } from '@angular/core';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import { NgFor } from '@angular/common';
import { ProfileCardComponent } from './profile-card/profile-card.component';
import { UserService } from '../services/user/user.service';
import { User } from '../interfaces/user.interface';
import { FormsModule } from '@angular/forms';
import { MobileFooterComponent } from '../mobile-footer/mobile-footer.component';

@Component({
  selector: 'app-discover',
  standalone: true,
  imports: [NavBarComponent, NgFor, ProfileCardComponent, FormsModule, MobileFooterComponent],
  templateUrl: './discover.component.html',
  styleUrl: './discover.component.scss'
})
export class DiscoverComponent implements OnInit {
  private userService = inject(UserService);
  users?: User[];
  city: string = "";

  ngOnInit(): void {
    this.userService.getUsers().subscribe(users => {
      this.users = users;
    });
  }

  onEnter() {
    if (this.city.length > 0) {
      this.userService.getUsersByCity(this.city).subscribe(response => {
        this.users = response
      });
    } else {
      this.userService.getUsers().subscribe(users => {
        this.users = users;
      });
    }
  }
}