import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SportsFormComponent } from './sports-form.component';

describe('SportsFormComponent', () => {
  let component: SportsFormComponent;
  let fixture: ComponentFixture<SportsFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SportsFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SportsFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
