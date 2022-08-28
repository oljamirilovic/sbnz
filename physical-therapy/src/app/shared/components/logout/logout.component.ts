import { Component, OnInit, Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationService } from 'src/app/auth/services/authentication-service/authentication.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss'],
})
export class LogoutComponent implements OnInit {
  @Output() onLogoutClose = new EventEmitter();

  constructor(
    private toastr: ToastrService,
    public router: Router,
    private authService: AuthenticationService
  ) {}

  ngOnInit(): void {}

  cancel() {
    this.onLogoutClose.emit(true);
  }

  confirm() {
    this.authService.logout().subscribe(response => {
      localStorage.removeItem('currentUser');
      this.router.navigate(['/therapy/auth/login']);
      this.toastr.success('Successful logout!');
      
    }, error => {
      this.toastr.error(error.error);
    })
  }

}
