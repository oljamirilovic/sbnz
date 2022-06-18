import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import {
  FormGroup,
  FormControl,
  Validators,
  FormBuilder,
} from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject } from 'rxjs';
import { AuthenticationService } from '../../services/authentication-service/authentication.service';
import { UserWithToken } from 'src/app/shared/models/user-with-token';
import { UserLogin } from 'src/app/shared/models/user-login';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss'],
})
export class LoginFormComponent implements OnInit {
  loginForm: FormGroup;

  hide = true;

  constructor(
    private fb: FormBuilder,
    public router: Router,
    private toastr: ToastrService,
    private authService: AuthenticationService
  ) {
    this.loginForm = this.fb.group({
      username: [null, Validators.required],
      password: [null, Validators.required],
    });
    const loggedUser = new BehaviorSubject<UserWithToken>(JSON.parse(localStorage.getItem('currentUser')!));
  }

  ngOnInit() {}

  login() {
    if (
      this.loginForm.value.username === null ||
      this.loginForm.value.password === null
    ) {
      this.toastr.error('All fields must be filled in!');
    } else {
      const loggedUser: UserLogin = {
        username: this.loginForm.value.username,
        password: this.loginForm.value.password,
      };

      this.authService.login(loggedUser).subscribe(
        (result) => {
          this.toastr.success('Successful login!');
          const user =  {
            token: result.token,
            username: result.username,
            userType: result.userType,
          };
          localStorage.setItem('currentUser', JSON.stringify(user));
          this.loginForm.reset();
          if (result.userType === 'THERAPIST') {
            this.router.navigate(['/therapist-dashboard']);
          } else if (result.userType === 'PATIENT') {
            this.router.navigate(['/patient-dashboard']);
          } 
          
        },
        (error) => {
          this.toastr.error(error.error);
        }
      );
    }
  }

 }
