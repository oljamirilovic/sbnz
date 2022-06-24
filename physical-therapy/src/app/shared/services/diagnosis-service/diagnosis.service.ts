import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Diagnosis } from '../../models/diagnosis';
import { Jmr } from '../../models/jmr';

@Injectable({
  providedIn: 'root'
})
export class DiagnosisService {
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient) {}

  getAllByPatientUsername(username: string): Observable<Array<Diagnosis>> {
    return this.http.get<Array<Diagnosis>>(
      `${environment.baseUrl}/${environment.diagnosis}/getAllByPatientUsername/${username}`
    );
  }

  checkNewTherapyAvailable(jmr: Jmr, diagnosisId: number): Observable<any>{
    return this.http.post(
      `${environment.baseUrl}/${environment.diagnosis}/checkNewTherapyAvailable/${diagnosisId}`, jmr, {
        headers: this.headers,
        responseType: 'text'
      }
    );
  }
}
