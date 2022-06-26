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
import { AppointmentService } from '../../services/appointment-service/appointment.service';
import { DiagnosisService } from '../../services/diagnosis-service/diagnosis.service';

@Component({
  selector: 'app-add-jmr',
  templateUrl: './add-jmr.component.html',
  styleUrls: ['./add-jmr.component.scss'],
})
export class AddJmrComponent implements OnInit {
  @Input() diagnosisId = 0;
  @Input() appointmentId = 0;
  @Output() onJMRClose = new EventEmitter();
  @Output() onJMRSubmitted = new EventEmitter();
  @ViewChild('elbow') elbow!: ElementRef;
  @ViewChild('shoulder') shoulder!: ElementRef;
  @ViewChild('knee') knee!: ElementRef;
  @ViewChild('score') score!: ElementRef;

  constructor(
    private toastr: ToastrService,
    private appointmentService: AppointmentService,
    private diagnosisService: DiagnosisService
  ) {}

  ngOnInit(): void {}

  cancel() {
    this.onJMRClose.emit();
  }

  finish() {
    if (
      (this.elbow.nativeElement.value != 0 &&
        this.knee.nativeElement.value != 0 &&
        this.shoulder.nativeElement.value != 0) ||
      this.score.nativeElement.value != 0
    ) {
      const jmr = {
        elbowFlexion: this.elbow.nativeElement.value,
        kneeFlexion: this.knee.nativeElement.value,
        shoulderFlexion: this.shoulder.nativeElement.value,
        flexionScore: this.score.nativeElement.value,
      };
      if (this.appointmentId != 0) {
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
      } else if (this.diagnosisId != 0) {
        this.diagnosisService
          .checkNewTherapyAvailable(jmr, this.diagnosisId)
          .subscribe({
            next: (response) => {
              this.onJMRSubmitted.emit();
            },
            error: (error) => {
              this.toastr.error(error.error);
            },
          });
      }
    } else {
      this.toastr.error("Can't leave fields empty");
    }
  }
}
