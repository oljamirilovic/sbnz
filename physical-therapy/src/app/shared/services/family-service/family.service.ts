import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Family } from '../../models/family';
import { FamilyTree } from '../../models/familyTree';

@Injectable({
  providedIn: 'root',
})
export class FamilyService {
  private headers = new HttpHeaders({
    Accept: 'application/json',
    'Content-Type': 'application/json',
    'X-Auth-Token': JSON.parse(localStorage.getItem('currentUser')!).token,
  });

  constructor(private http: HttpClient) {}

  getAllByChildUsername(username: string): Observable<FamilyTree> {
    return this.http.get<FamilyTree>(
      `${environment.baseUrl}/${environment.family}/getAllByChildUsername/${username}`
    );
  }

  startBackwardChaining(username: string, illness: string): Observable<string> {
    return this.http.get(
      `${environment.baseUrl}/${environment.family}/startBackwardChaining/${username}/${illness}`,
      { responseType: 'text' }
    );
  }

  getParentsByChildUsername(username: string): Observable<string[]> {
    return this.http.get<string[]>(
      `${environment.baseUrl}/${environment.family}/getParentsByChildUsername/${username}`,
      { headers: this.headers }
    );
  }
}
