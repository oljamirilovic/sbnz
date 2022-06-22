import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Appointment } from '../../models/appointment';
import { Patient } from '../../models/patient';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {
  constructor(private http: HttpClient) {}

  getAllAppointmentsByTherapist(username: string, searchTerm: string): Observable<Array<Appointment>> {
    return this.http.get<Array<Appointment>>(
      `${environment.baseUrl}/${environment.appointment}/getAllAppointmentsByTherapist/${username}/${searchTerm}`
    );
  }

  getPatientByAppointmentId(id: number): Observable<Patient> {
    return this.http.get<Patient>(
      `${environment.baseUrl}/${environment.appointment}/getPatientByAppointmentId/${id}`
    );
  }
}
