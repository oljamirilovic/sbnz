import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Illness } from '../../models/illness';

@Injectable({
  providedIn: 'root'
})
export class IllnessService {
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient) {}

  getAllIllnesses(): Observable<Array<Illness>> {
    return this.http.get<Array<Illness>>(
      `${environment.baseUrl}/${environment.illness}/getAllIllnesses`
    );
  }
}
