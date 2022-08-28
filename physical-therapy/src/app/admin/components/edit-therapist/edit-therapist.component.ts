import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { UserService } from 'src/app/shared/services/user-service/user.service';

@Component({
  selector: 'app-edit-therapist',
  templateUrl: './edit-therapist.component.html',
  styleUrls: ['./edit-therapist.component.scss'],
})
export class EditTherapistComponent implements OnInit {
  @Output() closeEditTherapist = new EventEmitter();
  @Input() therapistUsername = '';
  loginForm: FormGroup;
  hide = true;

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
    });
  }

  ngOnInit(): void {
    this.getUserData(this.therapistUsername);
  }

  getUserData(therapistUsername: string) {
    this.userService.getTherapist(therapistUsername).subscribe({
      next: (response) => {
        this.loginForm = this.fb.group({
          username: [response.username, Validators.required],
          password: [null, Validators.required],
          fn: [response.firstName, Validators.required],
          ln: [response.lastName, Validators.required],
        });
      },
      error: (error) => {
        this.toastr.error(error.error);
      },
    });
  }

  close() {
    this.closeEditTherapist.emit();
  }

  create() {
    var newTherapist = {
      username: this.loginForm.value.username,
      password: this.loginForm.value.password,
      firstName: this.loginForm.value.fn,
      lastName: this.loginForm.value.ln,
    };
  }
}
