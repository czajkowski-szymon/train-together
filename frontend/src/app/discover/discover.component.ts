import { Component } from '@angular/core';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import { UserProfile } from '../interfaces/user-profile.interface';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-discover',
  standalone: true,
  imports: [NavBarComponent, NgFor],
  templateUrl: './discover.component.html',
  styleUrl: './discover.component.scss'
})
export class DiscoverComponent {
  users: UserProfile[] = [
    {
      userId: 1,
      username: 'szymon',
      firstName: 'Szymon',
      city: 'Krakow',
      profilePictureId: '1.jpg'
    },
    {
      userId: 2,
      username: 'szymon',
      firstName: 'Szymon',
      city: 'Krakow',
      profilePictureId: '1.jpg'
    },
    {
      userId: 3,
      username: 'szymon',
      firstName: 'Szymon',
      city: 'Krakow',
      profilePictureId: '1.jpg'
    },
    {
      userId: 4,
      username: 'szymon',
      firstName: 'Szymon',
      city: 'Krakow',
      profilePictureId: '1.jpg'
    },
    {
      userId: 5,
      username: 'szymon',
      firstName: 'Szymon',
      city: 'Krakow',
      profilePictureId: '1.jpg'
    },
    {
      userId: 6,
      username: 'szymon',
      firstName: 'Szymon',
      city: 'Krakow',
      profilePictureId: '1.jpg'
    },
    {
      userId: 7,
      username: 'szymon',
      firstName: 'Szymon',
      city: 'Krakow',
      profilePictureId: '1.jpg'
    },
    {
      userId: 8,
      username: 'szymon',
      firstName: 'Szymon',
      city: 'Krakow',
      profilePictureId: '1.jpg'
    },
    {
      userId: 9,
      username: 'szymon',
      firstName: 'Szymon',
      city: 'Krakow',
      profilePictureId: '1.jpg'
    },
  ];
}
