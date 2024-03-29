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
import { ViewOnePatientComponent } from './components/view-one-patient/view-one-patient.component';
import { ViewAppointmentsComponent } from './components/view-appointments/view-appointments.component';
import { StartAppointmentComponent } from './components/start-appointment/start-appointment.component';
import { FcResultComponent } from './components/fc-result/fc-result.component';
import { FilterPatientsComponent } from './components/filter-patients/filter-patients.component';

@NgModule({
  declarations: [
    TherapistDashboardComponent,
    ViewPatientsComponent,
    ViewOnePatientComponent,
    ViewAppointmentsComponent,
    StartAppointmentComponent,
    FcResultComponent,
    FilterPatientsComponent,
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
export class TherapistModule {}
