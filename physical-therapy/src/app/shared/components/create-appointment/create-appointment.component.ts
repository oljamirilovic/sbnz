import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

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

  constructor(private fb: FormBuilder) {
    this.datePeriodForm = this.fb.group({
      dateFrom: [null, Validators.required],
    });
  }

  ngOnInit(): void {}

  close(){
    this.closeCreateAppointment.emit();
  }

  create(){
    //TODO createAppointment
    //var fromDate = this.datePeriodForm.value.dateFrom;
  }
}
