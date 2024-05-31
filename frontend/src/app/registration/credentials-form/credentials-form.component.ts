import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { City } from '../../interfaces/city.interface';
import { UserService } from '../../services/user/user.service';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-credentials-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './credentials-form.component.html',
  styleUrl: './credentials-form.component.scss'
})
export class CredentialsFormComponent {
  fb: FormBuilder = inject(FormBuilder);
  userService: UserService = inject(UserService);
  message!: string;
  isAvailable!: boolean; 
  @Output() formSubmitted: EventEmitter<any> = new EventEmitter();
  credentialsForm: FormGroup = this.fb.group({
    username: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required],
  }); 

  async areCredentialsAvailable(): Promise<boolean> {
    const username = this.credentialsForm.get('username')?.value;
    const email = this.credentialsForm.get('email')?.value;
    const response = await firstValueFrom(this.userService.areCredentialsAvailable(username, email));
    return response;
  }

  async onSubmit(): Promise<void> {
    this.isAvailable = await this.areCredentialsAvailable();
    console.log(this.isAvailable);
    if (this.credentialsForm.valid && this.isAvailable) {
      this.formSubmitted.emit(this.credentialsForm.value);
    } else {
      this.message = 'Email or username already taken';
    }
  }
}
