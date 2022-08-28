import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { UserService } from 'src/app/shared/services/user-service/user.service';

@Component({
  selector: 'app-new-therapist',
  templateUrl: './new-therapist.component.html',
  styleUrls: ['./new-therapist.component.scss']
})
export class NewTherapistComponent implements OnInit {
  @Output() closeNewTherapist = new EventEmitter();
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

  ngOnInit(): void {}

  close() {
    this.closeNewTherapist.emit();
  }

  create() {
    var newTherapist = {
      username: this.loginForm.value.username,
      password: this.loginForm.value.password,
      firstName: this.loginForm.value.fn,
      lastName: this.loginForm.value.ln,
    };

    this.userService.newTherapist(newTherapist).subscribe({
      next: (response) => {
        this.toastr.success(response);
        this.closeNewTherapist.emit();
      },
      error: (error) => {
        this.toastr.error(error.error);
      },
    });
  }

}
