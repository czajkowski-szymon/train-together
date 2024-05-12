import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { UserService } from '../services/user/user.service';
import { CityService } from '../services/city/city.service';
import { City } from '../interfaces/city.interface';

@Component({
  selector: 'app-registration',
  standalone: true,
  imports: [ReactiveFormsModule, RouterModule],
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.scss'
})
export class RegistrationComponent implements OnInit {
  fb: FormBuilder = inject(FormBuilder);
  userService: UserService = inject(UserService);
  cityService: CityService = inject(CityService);
  router: Router = inject(Router);
  cities?: City[] = [];
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

  ngOnInit(): void {
    this.cityService.getCities().subscribe(cities => {
      this.cities = cities;
    })
  }

  onSubmit(): void {
    this.userService.register({
      username: this.registerForm.get('username')?.value!,
      email: this.registerForm.get('email')?.value!,
      password: this.registerForm.get('password')?.value!,
      firstName: this.registerForm.get('firstName')?.value!,
      bio: this.registerForm.get('bio')?.value!,
      dateOfBirth: new Date(this.registerForm.get('dateOfBirth')?.value!),
      gender: this.registerForm.get('gender')?.value!,
      city: this.registerForm.get('city')?.value!
    })
    .subscribe((response) => {
      this.router.navigateByUrl("/login");
    });
  }
}
