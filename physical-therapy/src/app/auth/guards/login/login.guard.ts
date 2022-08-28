import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { JwtHelperService } from "@auth0/angular-jwt";
import { AuthenticationService } from '../../services/authentication-service/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class LoginGuard implements CanActivate {
  constructor(public auth: AuthenticationService, public router: Router) {}

  canActivate(): boolean {
    const token = localStorage.getItem("currentUser");
    const jwt: JwtHelperService = new JwtHelperService();

    if (this.auth.isLoggedIn()) {
      const userType = (jwt.decodeToken(token!)).role[0].authority;

      if (userType === 'ROLE_THERAPIST') {
        this.router.navigate(['/therapy/therapist/therapist-dashboard']);
      } else if (userType === 'ROLE_ADMIN') {
        this.router.navigate(['/therapy/admin/admin-dashboard']);
      } 

      return false;
    }
    return true;
  }
  
}
