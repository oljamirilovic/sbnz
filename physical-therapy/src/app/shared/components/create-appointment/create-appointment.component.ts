import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject } from 'rxjs';
import { UserWithToken } from '../../models/user-with-token';
import { AppointmentService } from '../../services/appointment-service/appointment.service';

@Component({
  selector: 'app-create-appointment',
  templateUrl: './create-appointment.component.html',
  styleUrls: ['./create-appointment.component.scss'],
})
export class CreateAppointmentComponent implements OnInit {
  @Output() closeCreateAppointment = new EventEmitter();
  @Input() patientUsername!: string;
  datePeriodForm: FormGroup;
  today: Date = new Date();

  constructor(
    private fb: FormBuilder,
    private toastr: ToastrService,
    private apService: AppointmentService
  ) {
    this.datePeriodForm = this.fb.group({
      dateFrom: [null, Validators.required],
    });
  }

  ngOnInit(): void {}

  close() {
    this.closeCreateAppointment.emit();
  }

  create() {
    const temp = new BehaviorSubject<UserWithToken>(
      JSON.parse(localStorage.getItem('currentUser')!)
    );
    var therapist = temp.value.username;
    var fromDate = this.datePeriodForm.value.dateFrom;
    let newApp = {
      date: fromDate,
      patientUsername: this.patientUsername,
      therapistUsername: therapist,
    };
    this.apService.newAppointment(newApp).subscribe({
      next: (response) => {
        this.toastr.success(response);
        this.closeCreateAppointment.emit();
      },
      error: (error) => {
        this.toastr.error(error.error);
      },
    });
  }
}
