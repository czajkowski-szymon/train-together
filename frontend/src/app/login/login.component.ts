import { Component, inject } from '@angular/core';
import {
  FormBuilder,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';
import { catchError, of } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  fb: FormBuilder = inject(FormBuilder);
  authService: AuthService = inject(AuthService);
  router: Router = inject(Router);
  message!: string;
  loginForm = this.fb.group({
    username: ['', Validators.required],
    password: ['', Validators.required],
  });

  onSubmit(): void {
    this.authService
      .login({
        username: this.loginForm.get('username')?.value!,
        password: this.loginForm.get('password')?.value!,
      })
      .pipe(
        catchError((error: HttpErrorResponse) => {
          if (error.status === 401 || error.status === 404) {
            this.message = 'Wrong email or password';
          }
          return of(null);
        })
      )
      .subscribe((response) => {
        if (response) {
          localStorage.setItem('token', response.token);
          this.authService.currentUserSignal.set(response.user);
          this.router.navigateByUrl('/discover');
        }
      });
  }
}
