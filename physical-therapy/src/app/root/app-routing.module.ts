import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';

const routes: Routes = [
  {
    path: 'therapy',
    component: AppComponent,
    children: [
      {
        path: 'therapist',
        loadChildren: () =>
          import('./../therapist/therapist.module').then((m) => m.TherapistModule),
      },
      {
        path: 'auth',
        loadChildren: () =>
          import('./../auth/auth.module').then((m) => m.AuthModule),
      },
    ],
  },
  {
    path: '',
    redirectTo: '/therapy/auth/login',
    pathMatch: 'full',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
