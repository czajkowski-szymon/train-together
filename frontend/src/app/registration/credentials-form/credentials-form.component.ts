import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { City } from '../../interfaces/city.interface';

@Component({
  selector: 'app-credentials-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './credentials-form.component.html',
  styleUrl: './credentials-form.component.scss'
})
export class CredentialsFormComponent {
  fb: FormBuilder = inject(FormBuilder);
  @Output() formSubmitted: EventEmitter<any> = new EventEmitter();
  credentialsForm: FormGroup = this.fb.group({
    username: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required],
  }); 

  onSubmit(): void {
    if (this.credentialsForm.valid) {
      this.formSubmitted.emit(this.credentialsForm.value);
    }
  }
}
