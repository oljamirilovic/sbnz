import {
  Component,
  EventEmitter,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSelect } from '@angular/material/select';
import { ToastrService } from 'ngx-toastr';
import { UserService } from 'src/app/shared/services/user-service/user.service';

@Component({
  selector: 'app-new-patient',
  templateUrl: './new-patient.component.html',
  styleUrls: ['./new-patient.component.scss'],
})
export class NewPatientComponent implements OnInit {
  @ViewChild('pa') matSelect!: MatSelect;
  @Output() closeNewPatient = new EventEmitter();
  loginForm: FormGroup;
  hide = true;
  paOptions: string[] = ['SEDENTARY', 'MODERATE', 'VERY_ACTIVE'];
  genderOptions: string[] = ['MALE', 'FEMALE'];

  constructor(
    private fb: FormBuilder,
    private toastr: ToastrService,
    private userService: UserService
  ) {
    this.loginForm = this.fb.group({
      username: [null, Validators.required],
      password: [null, Validators.required],
      fn: [null, Validators.required],
      ln: [null, Validators.required],
      age: [null, Validators.required],
      gender: [null, Validators.required],
      bmd: [null, Validators.required],
      pa: [null, Validators.required],
      p1: [null],
      p2: [null],
    });
  }

  ngOnInit(): void {}

  close() {
    this.closeNewPatient.emit();
  }

  create() {
    var newPat = {
      username: this.loginForm.value.username,
      password: this.loginForm.value.password,
      firstName: this.loginForm.value.fn,
      lastName: this.loginForm.value.ln,
      age: this.loginForm.value.age,
      gender: this.loginForm.value.gender,
      bmd: this.loginForm.value.bmd,
      pa: this.loginForm.value.pa,
      p1: this.loginForm.value.p1,
      p2: this.loginForm.value.p2,
    };

    this.userService.newPatient(newPat).subscribe({
      next: (response) => {
        this.toastr.success(response);
        this.closeNewPatient.emit();
      },
      error: (error) => {
        this.toastr.error(error.error);
      },
    });
  }
}
