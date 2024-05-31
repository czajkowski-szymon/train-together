import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CredentialsFormComponent } from './credentials-form.component';

describe('CredentialsFormComponent', () => {
  let component: CredentialsFormComponent;
  let fixture: ComponentFixture<CredentialsFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CredentialsFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CredentialsFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
