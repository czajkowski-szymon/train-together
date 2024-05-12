import { inject } from "@angular/core"
import { AuthService } from "../services/auth/auth.service"
import { Router } from "@angular/router";

export const authGuard = () => {
  const authService: AuthService = inject(AuthService);
  const router: Router = inject(Router);

  if (authService.isAuthenticated()) {
     return true; 
  } else {
    router.navigateByUrl('/login');
    return false;
  }
}