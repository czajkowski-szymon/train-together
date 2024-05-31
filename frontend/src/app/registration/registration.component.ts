import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { UserService } from '../services/user/user.service';
import { CityService } from '../services/city/city.service';
import { City } from '../interfaces/city.interface';
import { CredentialsFormComponent } from './credentials-form/credentials-form.component';
import { PersonalInfoFormComponent } from './personal-info-form/personal-info-form.component';
import { RegistrationRequest } from '../interfaces/registration-request.interface';
import { SportService } from '../services/sport/sport.service';
import { Sport } from '../interfaces/sport.interface';
import { SportsFormComponent } from './sports-form/sports-form.component';

@Component({
  selector: 'app-registration',
  standalone: true,
  imports: [
    ReactiveFormsModule, 
    RouterModule, 
    CredentialsFormComponent, 
    PersonalInfoFormComponent, 
    SportsFormComponent,
  ],
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.scss'
})
export class RegistrationComponent implements OnInit {
  fb: FormBuilder = inject(FormBuilder);
  userService: UserService = inject(UserService);
  cityService: CityService = inject(CityService);
  sportService: SportService = inject(SportService);
  router: Router = inject(Router);
  cities?: City[] = [];
  sports: Sport[] =[];
  selectedFile: File | null = null;
  registerStep: number = 1;
  registrationRequest: RegistrationRequest = {
    username: "",
    email: "",
    password: "",
    firstName: "",
    dateOfBirth: undefined,
    gender: "",
    bio: "",
    city: "",
    sportIds: []
  };

  ngOnInit(): void {
    this.cityService.getCities().subscribe(cities => {
      this.cities = cities;
    })

    this.sportService.getSports().subscribe(sports => {
      this.sports = sports;
    })
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    }
  }

  handleCredentialsFormSubmission(data: any): void {
    this.registrationRequest.username = data.username;
    this.registrationRequest.email = data.email;
    this.registrationRequest.password = data.password;
    this.registerStep = 2;
  }

  handlePersonalInfoFormSubmission(data: any): void {
    this.registrationRequest.firstName = data.firstName;
    this.registrationRequest.dateOfBirth = data.dateOfBirth;
    this.registrationRequest.gender = data.gender;
    this.registrationRequest.bio = data.bio;
    this.registrationRequest.city = data.city;
    this.registerStep = 3;
  }

  handleSportsFormSubmission(data: any): void {
    this.registrationRequest.sportIds = data;
    this.registerStep = 4;
  }

  register(): void {
    this.userService.register(this.registrationRequest).subscribe((response) => {
      if (this.selectedFile) {
        const formData = new FormData();
        formData.append('file', this.selectedFile, this.selectedFile.name);
      
        this.userService.uploadFile(formData, response.userId).subscribe();
      }
      this.router.navigateByUrl("/login");
    });
  }
}
