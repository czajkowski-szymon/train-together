import { HttpClientModule } from '@angular/common/http';
import { Component, OnInit, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AuthService } from './services/auth/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HttpClientModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  title = 'train-together';
  authService: AuthService = inject(AuthService);

  ngOnInit(): void {
    this.authService.authenticate().subscribe(response => {
      this.authService.currentUserSignal.set(response);
    });
  }
}
