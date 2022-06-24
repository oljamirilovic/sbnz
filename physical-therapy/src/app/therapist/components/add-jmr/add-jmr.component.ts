import { LiveAnnouncer } from '@angular/cdk/a11y';
import {
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { AppointmentService } from 'src/app/shared/services/appointment-service/appointment.service';

@Component({
  selector: 'app-add-jmr',
  templateUrl: './add-jmr.component.html',
  styleUrls: ['./add-jmr.component.scss'],
})
export class AddJmrComponent implements OnInit {
  @Input() appointmentId = 0;
  @Output() onJMRClose = new EventEmitter();
  @Output() onJMRSubmitted = new EventEmitter();
  @ViewChild('elbow') elbow!: ElementRef;
  @ViewChild('shoulder') shoulder!: ElementRef;
  @ViewChild('knee') knee!: ElementRef;
  @ViewChild('score') score!: ElementRef;

  constructor(
    private toastr: ToastrService,
    private liveAnnouncer: LiveAnnouncer,
    private appointmentService: AppointmentService
  ) {}

  ngOnInit(): void {}

  cancel() {
    this.onJMRClose.emit();
  }

  finish() {
    if (
      this.score.nativeElement.value == 0 &&
      this.knee.nativeElement.value == 0 &&
      this.shoulder.nativeElement.value == 0 &&
      this.elbow.nativeElement.value == 0
    ) {
      this.toastr.error("Can't leave all fields empty");
    } else {
      const jmr = {
        elbowFlexion: this.elbow.nativeElement.value,
        kneeFlexion: this.knee.nativeElement.value,
        shoulderFlexion: this.shoulder.nativeElement.value,
        flexionScore: this.score.nativeElement.value,
      };
      this.appointmentService
        .addJmrToAppointment(jmr, this.appointmentId)
        .subscribe({
          next: (response) => {
            this.onJMRSubmitted.emit();
          },
          error: (error) => {
            this.toastr.error(error.error);
          },
        });
    }
  }
}
