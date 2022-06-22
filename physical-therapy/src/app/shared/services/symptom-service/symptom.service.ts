import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Symptom } from '../../models/symptom';

@Injectable({
  providedIn: 'root'
})
export class SymptomService {
  constructor(private http: HttpClient) {}

  getAllSymptoms(searchTerm:string): Observable<Array<Symptom>> {
    return this.http.get<Array<Symptom>>(
      `${environment.baseUrl}/${environment.symptom}/getAllSymptoms/${searchTerm}`
    );
  }
}
