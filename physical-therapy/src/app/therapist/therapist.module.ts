import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TherapistDashboardComponent } from './pages/therapist-dashboard/therapist-dashboard.component';
import { ReactiveFormsModule } from '@angular/forms';
import { TherapistRoutes } from './therapist.routes';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { MaterialModule } from '../root/material-module';
import { ToastrModule } from 'ngx-toastr';
import { SharedModule } from '../shared/shared.module';
import { ViewPatientsComponent } from './components/view-patients/view-patients.component';
import { LogoutComponent } from './components/logout/logout.component';
import { ViewOnePatientComponent } from './components/view-one-patient/view-one-patient.component';
import { ViewAppointmentsComponent } from './components/view-appointments/view-appointments.component';
import { StartAppointmentComponent } from './components/start-appointment/start-appointment.component';
import { AddJmrComponent } from './components/add-jmr/add-jmr.component';



@NgModule({
  declarations: [
    LogoutComponent,
    TherapistDashboardComponent,
    ViewPatientsComponent,
    ViewOnePatientComponent,
    ViewAppointmentsComponent,
    StartAppointmentComponent,
    AddJmrComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule.forChild(TherapistRoutes),
    HttpClientModule,
    MaterialModule,
    ToastrModule.forRoot({
      timeOut: 3000,
      positionClass: 'toast-top-right',
      preventDuplicates: true,
    }),
    SharedModule,
  ],
})
export class TherapistModule { }
