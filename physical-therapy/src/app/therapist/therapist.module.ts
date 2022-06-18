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



@NgModule({
  declarations: [
    TherapistDashboardComponent
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
