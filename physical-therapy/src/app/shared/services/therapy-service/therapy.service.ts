import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Therapy } from '../../models/therapy';

@Injectable({
  providedIn: 'root'
})
export class TherapyService {
  constructor(private http: HttpClient) {}

  getAllTherapiesByDiagnosisId(diagnosisId:number): Observable<Array<Therapy>> {
    return this.http.get<Array<Therapy>>(
      `${environment.baseUrl}/${environment.therapy}/getAllTherapiesByDiagnosisId/${diagnosisId}`
    );
  }
}
