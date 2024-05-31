import { Component, EventEmitter, Input, OnInit, Output, inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Sport } from '../../interfaces/sport.interface';

@Component({
  selector: 'app-sports-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './sports-form.component.html',
  styleUrl: './sports-form.component.scss'
})
export class SportsFormComponent implements OnInit {
  fb: FormBuilder = inject(FormBuilder);
  @Output() formSubmitted: EventEmitter<any> = new EventEmitter();
  @Input() sports: Sport[] = [];
  sportsForm!: FormGroup;

  ngOnInit(): void {
    this.initForm();
  }

  initForm(): void {
    const formControls = new Map<number, FormControl>();

    this.sports.forEach(sport => {
      formControls.set(sport.sportId, new FormControl(false));
    });

    this.sportsForm = this.fb.group(Object.fromEntries(formControls));
  }

  onSubmit(): void {
    const data = this.sportsForm.value;
    const trueKeys = Object.keys(data).filter(key => data[key] === true).map(Number);
    if (this.sportsForm.valid) {
      this.formSubmitted.emit(trueKeys);
    }
  }
}
