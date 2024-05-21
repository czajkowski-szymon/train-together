import { Component, inject } from '@angular/core';
import { AuthService } from '../services/auth/auth.service';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-nav-bar',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.scss'
})
export class NavBarComponent {
  authService: AuthService = inject(AuthService);
  router: Router = inject(Router);

  logout(): void {
    localStorage.setItem('token', '');
    this.authService.currentUserSignal.set(null);
    this.router.navigateByUrl("/login");
  }
}
