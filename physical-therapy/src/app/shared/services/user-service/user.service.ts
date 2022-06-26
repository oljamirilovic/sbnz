import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Patient } from '../../models/patient';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient) {}

  getAllPatients(searchTerm: string): Observable<Array<Patient>> {
    return this.http.get<Array<Patient>>(
      `${environment.baseUrl}/${environment.users}/getAllPatients/${searchTerm}`
    );
  }

  getPatient(username: string): Observable<Patient> {
    return this.http.get<Patient>(
      `${environment.baseUrl}/${environment.users}/getPatient/${username}`
    );
  }

  newPatient(newPatient: any): Observable<any>{
    return this.http.post(
      `${environment.baseUrl}/${environment.users}/newPatient`, newPatient, {
        headers: this.headers,
        responseType: 'text'
      }
    );
  }

  getAllPatientsWithResolvableTherapies(): Observable<Array<Patient>> {
    return this.http.get<Array<Patient>>(
      `${environment.baseUrl}/${environment.users}/resolvableTherapies`
    );
  }

  getAllPatientsWithPotentialAbuse(): Observable<Array<Patient>> {
    return this.http.get<Array<Patient>>(
      `${environment.baseUrl}/${environment.users}/potentialAbuse`
    );
  }

  getAllPatientsWithResolvableTherapiesByType(type: string): Observable<Array<Patient>> {
    return this.http.get<Array<Patient>>(
      `${environment.baseUrl}/${environment.users}/resolvableTherapiesByType/${type}`
    );
  }
}
