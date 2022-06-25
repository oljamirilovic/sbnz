import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Appointment } from '../../models/appointment';
import { Jmr } from '../../models/jmr';
import { NewAppointment } from '../../models/newAppointment';
import { Patient } from '../../models/patient';
import { Symptom } from '../../models/symptom';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

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

  addSymptomsToAppointment(symptomList: Array<Symptom>, appointmentId: number): Observable<any>{
    return this.http.post(
      `${environment.baseUrl}/${environment.appointment}/addSymptomsToAppointment/${appointmentId}`, symptomList, {
        headers: this.headers,
        responseType: 'text'
      }
    );
  }

  addJmrToAppointment(jmr: Jmr, appointmentId: number): Observable<any>{
    return this.http.post(
      `${environment.baseUrl}/${environment.appointment}/addJmrToAppointment/${appointmentId}`, jmr, {
        headers: this.headers,
        responseType: 'text'
      }
    );
  }

  startForwardChaining(id: number): Observable<string> {
    return this.http.get(
      `${environment.baseUrl}/${environment.appointment}/startForwardChaining/${id}`,
      { responseType: 'text' }
    );
  }

  isAppointmentResolved(id: number): Observable<any> {
    return this.http.get<any>(
      `${environment.baseUrl}/${environment.appointment}/isAppointmentResolved/${id}`
    );
  }

  newAppointment(appointment: NewAppointment): Observable<any>{
    return this.http.post(
      `${environment.baseUrl}/${environment.appointment}/newAppointment`, appointment, {
        headers: this.headers,
        responseType: 'text'
      }
    );
  }
}
