import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Patient } from '../../models/patient';
import { Therapist } from '../../models/therapist';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private headers = new HttpHeaders({
    Accept: 'application/json',
    'Content-Type': 'application/json',
    'X-Auth-Token': JSON.parse(localStorage.getItem('currentUser')!).token,
  });

  constructor(private http: HttpClient) {}

  getAllPatients(searchTerm: string): Observable<Array<Patient>> {
    return this.http.get<Array<Patient>>(
      `${environment.baseUrl}/${environment.users}/getAllPatients/${searchTerm}`,
      { headers: this.headers }
    );
  }

  getPatient(username: string): Observable<Patient> {
    return this.http.get<Patient>(
      `${environment.baseUrl}/${environment.users}/getPatient/${username}`,
      { headers: this.headers }
    );
  }

  newPatient(newPatient: any): Observable<any> {
    return this.http.post(
      `${environment.baseUrl}/${environment.users}/newPatient`,
      newPatient,
      {
        headers: this.headers,
        responseType: 'text',
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

  getAllPatientsWithResolvableTherapiesByType(
    type: string
  ): Observable<Array<Patient>> {
    return this.http.get<Array<Patient>>(
      `${environment.baseUrl}/${environment.users}/resolvableTherapiesByType/${type}`
    );
  }

  deletePatient(username: string): Observable<Patient> {
    return this.http.get<Patient>(
      `${environment.baseUrl}/${environment.users}/deletePatient/${username}`,
      { headers: this.headers }
    );
  }

  newTherapist(newTherapist: any): Observable<any> {
    return this.http.post(
      `${environment.baseUrl}/${environment.users}/newTherapist`,
      newTherapist,
      {
        headers: this.headers,
        responseType: 'text',
      }
    );
  }

  getAllTherapists(searchTerm: string): Observable<Array<Therapist>> {
    return this.http.get<Array<Therapist>>(
      `${environment.baseUrl}/${environment.users}/getAllTherapists/${searchTerm}`,
      { headers: this.headers }
    );
  }

  getTherapist(username: string): Observable<Therapist> {
    return this.http.get<Therapist>(
      `${environment.baseUrl}/${environment.users}/getTherapist/${username}`,
      { headers: this.headers }
    );
  }

  deleteTherapist(username: string): Observable<string> {
    return this.http.get<string>(
      `${environment.baseUrl}/${environment.users}/deleteTherapist/${username}`,
      { headers: this.headers }
    );
  }
}
