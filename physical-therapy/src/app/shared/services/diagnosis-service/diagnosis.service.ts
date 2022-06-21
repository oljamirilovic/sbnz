import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Diagnosis } from '../../models/diagnosis';

@Injectable({
  providedIn: 'root'
})
export class DiagnosisService {

  constructor(private http: HttpClient) {}

  getAllByPatientUsername(username: string): Observable<Array<Diagnosis>> {
    return this.http.get<Array<Diagnosis>>(
      `${environment.baseUrl}/${environment.diagnosis}/getAllByPatientUsername/${username}`
    );
  }
}
