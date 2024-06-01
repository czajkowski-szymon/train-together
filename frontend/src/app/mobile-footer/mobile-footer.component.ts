import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';

@Component({
  selector: 'app-mobile-footer',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './mobile-footer.component.html',
  styleUrl: './mobile-footer.component.scss'
})
export class MobileFooterComponent {
  authService: AuthService = inject(AuthService);
  router: Router = inject(Router);

  logout(): void {
    console.log('halo');
    localStorage.setItem('token', '');
    this.authService.currentUserSignal.set(null);
    this.router.navigateByUrl("/login");
  }
}
