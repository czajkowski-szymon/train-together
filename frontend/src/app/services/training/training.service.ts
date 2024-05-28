import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Training } from '../../interfaces/training.interface';
import { TrainingInvite } from '../../interfaces/training-invite.interface';

@Injectable({
  providedIn: 'root'
})
export class TrainingService {
  private trainingsApiUrl = 'http://localhost:8080/api/v1/trainings';
  private http = inject(HttpClient);

  getUpcomingTrainings(): Observable<Array<Training>> {
    return this.http.get<Array<Training>>(this.trainingsApiUrl + '/upcoming');
  }

  getPastTrainings(): Observable<Array<Training>> {
    return this.http.get<Array<Training>>(this.trainingsApiUrl + '/past');
  }

  getTrainingInvites(): Observable<Array<TrainingInvite>> {
    return this.http.get<Array<TrainingInvite>>(this.trainingsApiUrl + '/invitations/received');
  }
}
