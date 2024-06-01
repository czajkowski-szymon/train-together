import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { City } from '../../interfaces/city.interface';
import { Observable } from 'rxjs';

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
  submitted = false;
  personalInfoForm: FormGroup = this.fb.group({
    firstName: ['', Validators.required],
    bio: '',
    dateOfBirth: ['', Validators.required, this.minAgeValidator(18)],
    gender: ['', Validators.required],
    city: ['', Validators.required]
  })

  get personalInfoFormControl() {
    return this.personalInfoForm.controls;
  }

  minAgeValidator(minAge: number) {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      return new Observable(observer => {
        if (!control.value) {
          observer.next(null);
          observer.complete();
        }

        const birthDate = new Date(control.value);
        const today = new Date();
        let age = today.getFullYear() - birthDate.getFullYear();
        const monthDifference = today.getMonth() - birthDate.getMonth();
        if (monthDifference < 0 || (monthDifference === 0 && today.getDate() < birthDate.getDate())) {
          age--;
        }

        if (age >= minAge) {
          observer.next(null);
        } else {
          observer.next({ minAge: { requiredAge: minAge, actualAge: age } });
        }
        observer.complete();
      });
    };
  }

  onSubmit(): void {
    if (this.personalInfoForm.valid) {
      this.formSubmitted.emit(this.personalInfoForm.value);
    }
  }
}
