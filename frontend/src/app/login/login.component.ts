import { Component, inject } from '@angular/core';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  fb: FormBuilder = inject(FormBuilder);
  authService: AuthService = inject(AuthService);
  router: Router = inject(Router); 
  loginForm = this.fb.group({
    username: ['', Validators.required],
    password: ['', Validators.required]
  });

  onSubmit(): void {
    this.authService.login({
      username: this.loginForm.get('username')?.value!,
      password: this.loginForm.get('password')?.value!
    })
    .subscribe(response => {
      localStorage.setItem('token', response.token);
      // localStorage.setItem('currentUser', response.user.username);
      this.authService.currentUserSignal.set(response.user);
      this.router.navigateByUrl('/discover');
    });
  }
}
