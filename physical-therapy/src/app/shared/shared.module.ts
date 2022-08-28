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
import { ShowFamilyComponent } from './components/show-family/show-family.component';
import { ViewTherapiesComponent } from './components/view-therapies/view-therapies.component';
import { AddJmrComponent } from './components/add-jmr/add-jmr.component';
import { CreateAppointmentComponent } from './components/create-appointment/create-appointment.component';
import { LogoutComponent } from './components/logout/logout.component';
import { NewPatientComponent } from './components/new-patient/new-patient.component';
import { EditPatientComponent } from './components/edit-patient/edit-patient.component';

@NgModule({
  declarations: [
    NewPatientComponent,
    LogoutComponent,
    ShowFamilyComponent,
    ViewTherapiesComponent,
    AddJmrComponent,
    CreateAppointmentComponent,
    EditPatientComponent,
  ],

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
  exports: [
    ShowFamilyComponent,
    AddJmrComponent,
    ViewTherapiesComponent,
    CreateAppointmentComponent,
    LogoutComponent,
    NewPatientComponent,
    EditPatientComponent
  ],
  providers: [
    UserService,
    DiagnosisService,
    AppointmentService,
    SymptomService,
  ],
})
export class SharedModule {}
