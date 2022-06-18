import { Routes } from "@angular/router";
import { RoleGuard } from "../auth/guards/role/role.guard";
import { TherapistDashboardComponent } from "./pages/therapist-dashboard/therapist-dashboard.component";

export const TherapistRoutes: Routes = [
  {
    path: "therapist-dashboard",
    pathMatch: "full",
    component: TherapistDashboardComponent,
    /*canActivate: [RoleGuard],
    data: { expectedRoles: 'THERAPIST' },*/
  },
];
