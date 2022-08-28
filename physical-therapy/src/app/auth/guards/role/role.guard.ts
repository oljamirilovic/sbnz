import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { AuthenticationService } from '../../services/authentication-service/authentication.service';

@Injectable({
  providedIn: 'root',
})
export class RoleGuard implements CanActivate {
  constructor(public auth: AuthenticationService, public router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRole: string = route.data['expectedRoles'];
    const token = localStorage.getItem('currentUser');

    if (!token) {
      this.router.navigate(['/login']);
      return false;
    }

    const user: {
      token: string;
      username: string;
      userType: string;
    } = JSON.parse(token);
    const userType = user.userType;

    if (userType === expectedRole) {
      return true;
    } else {
      if (userType === 'THERAPIST') {
        this.router.navigate(['/therapy/therapist/therapist-dashboard']);
      } else if (userType === 'ADMIN') {
        this.router.navigate(['/therapy/admin/admin-dashboard']);
      } 
      return false;
    }

    return true;
  }
}
