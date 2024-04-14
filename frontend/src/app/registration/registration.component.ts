import { Component, inject } from '@angular/core';
import { EmailValidator, FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-registration',
  standalone: true,
  imports: [ReactiveFormsModule, RouterModule],
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.scss'
})
export class RegistrationComponent {
  fb: FormBuilder = inject(FormBuilder);
  registerForm = this.fb.group({
    username: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required],
    confirmedPassword: ['', Validators.required],
    firstName: ['', Validators.required],
    bio: '',
    dateOfBirth: ['', Validators.required],
    gender: ['', Validators.required],
    city: ['', Validators.required]
  });

  onSubmit(): void {
    
  }
}
