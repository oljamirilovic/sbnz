import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserService } from 'src/app/shared/services/user-service/user.service';

@Component({
  selector: 'app-confirm-action',
  templateUrl: './confirm-action.component.html',
  styleUrls: ['./confirm-action.component.scss'],
})
export class ConfirmActionComponent implements OnInit {
  @Input() username = '';
  @Input() isPatient = false;
  @Output() onConfirmActionClose = new EventEmitter();
  @Output() onConfirmActionConfirm = new EventEmitter();

  constructor(
    private toastr: ToastrService,
    public router: Router,
    private userService: UserService
  ) {}

  ngOnInit(): void {}

  cancel() {
    this.onConfirmActionClose.emit(true);
  }

  confirm() {
    if (this.isPatient) {
      this.userService.deletePatient(this.username).subscribe({
        next: (response) => {
          this.onConfirmActionConfirm.emit(true);
        },
        error: (error) => {
          this.toastr.error(error.error);
        },
      });
    } else {
      this.userService.deleteTherapist(this.username).subscribe({
        next: (response) => {
          this.onConfirmActionConfirm.emit(false);
        },
        error: (error) => {
          this.toastr.error(error.error);
        },
      });
    }
  }
}
