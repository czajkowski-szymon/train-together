import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { City } from '../../interfaces/city.interface';

@Component({
  selector: 'app-personal-info-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './personal-info-form.component.html',
  styleUrl: './personal-info-form.component.scss'
})
export class PersonalInfoFormComponent {
  fb: FormBuilder = inject(FormBuilder);
  @Input() cities?: City[] = [];
  @Output() formSubmitted: EventEmitter<any> = new EventEmitter();
  personalInfoForm: FormGroup = this.fb.group({
    firstName: ['', Validators.required],
    bio: '',
    dateOfBirth: ['', Validators.required],
    gender: ['', Validators.required],
    city: ['', Validators.required]
  })

  onSubmit(): void {
    if (this.personalInfoForm.valid) {
      this.formSubmitted.emit(this.personalInfoForm.value);
    }
  }
}
