import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSelect } from '@angular/material/select';
import { ToastrService } from 'ngx-toastr';
import { FamilyService } from '../../services/family-service/family.service';
import { UserService } from '../../services/user-service/user.service';

@Component({
  selector: 'app-edit-patient',
  templateUrl: './edit-patient.component.html',
  styleUrls: ['./edit-patient.component.scss'],
})
export class EditPatientComponent implements OnInit {
  @ViewChild('pa') matSelect!: MatSelect;
  @Output() closeEditPatient = new EventEmitter();
  @Input() patientUsername = '';
  loginForm: FormGroup;
  hide = true;
  paOptions: string[] = ['SEDENTARY', 'MODERATE', 'VERY_ACTIVE'];
  genderOptions: string[] = ['MALE', 'FEMALE'];

  constructor(
    private fb: FormBuilder,
    private toastr: ToastrService,
    private userService: UserService,
    private familyService: FamilyService
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

  ngOnInit(): void {
    this.getUserData(this.patientUsername);
  }

  getUserData(patientUsername: string) {
    this.userService.getPatient(patientUsername).subscribe({
      next: (response) => {
        this.loginForm = this.fb.group({
          username: [response.username, Validators.required],
          password: [null, Validators.required],
          fn: [response.firstName, Validators.required],
          ln: [response.lastName, Validators.required],
          age: [response.age, Validators.required],
          gender: [response.gender, Validators.required],
          bmd: [response.bmd, Validators.required],
          pa: [response.pa, Validators.required],
          p1: [null],
          p2: [null],
        });
      },
      error: (error) => {
        this.toastr.error(error.error);
      },
    });
    this.familyService.getParentsByChildUsername(patientUsername).subscribe({
      next: (response) => {
        if (response[0] != '' && response[1] != '') {
          this.loginForm = this.fb.group({
            p1: [response[0]],
            p2: [response[1]],
          });
        }
        else if (response[1] != '') {
          this.loginForm = this.fb.group({
            p1: [response[1]],
          });
        }
        else if (response[0] != '') {
          this.loginForm = this.fb.group({
            p1: [response[0]],
          });
        }
      },
      error: (error) => {
        this.toastr.info(error.error);
      },
    });
  }

  close() {
    this.closeEditPatient.emit();
  }

  save() {
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
    this.closeEditPatient.emit();
    /*
    this.userService.newPatient(newPat).subscribe({
      next: (response) => {
        this.toastr.success(response);
        this.closeEditPatient.emit();
      },
      error: (error) => {
        this.toastr.error(error.error);
      },
    });*/
  }
}
