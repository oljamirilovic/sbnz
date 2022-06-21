import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Patient } from '../../models/patient';

@Injectable({
  providedIn: 'root',
})
export class UserService {
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
}
