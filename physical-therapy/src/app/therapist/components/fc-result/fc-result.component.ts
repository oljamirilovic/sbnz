import {
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AppointmentService } from 'src/app/shared/services/appointment-service/appointment.service';

@Component({
  selector: 'app-fc-result',
  templateUrl: './fc-result.component.html',
  styleUrls: ['./fc-result.component.scss'],
})
export class FcResultComponent implements OnInit {
  @Input() appointmentId = 0;
  @Output() onFcResultCancel = new EventEmitter();
  @Output() onFcResultFinish = new EventEmitter();
  @ViewChild('notes') notes!: ElementRef;
  @Input() currentNotes!: string;
  appointmentResolved: boolean;

  constructor(
    public router: Router,
    private toastr: ToastrService,
    public appointmentService: AppointmentService
  ) {
    this.appointmentResolved = false;
  }

  ngOnInit(): void {
    this.isAppointmetnResolved();
  }

  isAppointmetnResolved() {
    this.appointmentService
      .isAppointmentResolved(this.appointmentId)
      .subscribe({
        next: (response) => {
          this.appointmentResolved = response;
        },
        error: (error) => {
          this.toastr.error(error.error);
        },
      });
  }

  cancel() {
    this.onFcResultCancel.emit();
  }

  finish() {
    this.onFcResultFinish.emit();
  }
}
