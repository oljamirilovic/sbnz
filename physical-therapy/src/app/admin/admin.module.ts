import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { MaterialModule } from '../root/material-module';
import { ToastrModule } from 'ngx-toastr';
import { SharedModule } from '../shared/shared.module';
import { AdminRoutes } from './admin.routes';
import { AdminDashboardComponent } from './pages/admin-dashboard/admin-dashboard.component';
import { ViewTherapistsComponent } from './components/view-therapists/view-therapists.component';
import { NewTherapistComponent } from './components/new-therapist/new-therapist.component';
import { ViewAllPatientsComponent } from './components/view-patients/view-patients.component';
import { ConfirmActionComponent } from './components/confirm-action/confirm-action.component';
import { EditTherapistComponent } from './components/edit-therapist/edit-therapist.component';

@NgModule({
  declarations: [
    AdminDashboardComponent,
    ViewTherapistsComponent,
    NewTherapistComponent,
    ViewAllPatientsComponent,
    ConfirmActionComponent,
    EditTherapistComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule.forChild(AdminRoutes),
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
export class AdminModule {}
