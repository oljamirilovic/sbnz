import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../root/material-module';
import { ToastrModule } from 'ngx-toastr';
import { UserService } from './services/user-service/user.service';
import { DiagnosisService } from './services/diagnosis-service/diagnosis.service';
import { AppointmentService } from './services/appointment-service/appointment.service';
import { SymptomService } from './services/symptom-service/symptom.service';

@NgModule({
  declarations: [],

  imports: [
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    MaterialModule,
    ToastrModule.forRoot({
      timeOut: 3000,
      positionClass: 'toast-top-right',
      preventDuplicates: true,
    }),
  ],
  exports: [],
  providers: [
    UserService,
    DiagnosisService,
    AppointmentService,
    SymptomService,
  ],
})
export class SharedModule {}
