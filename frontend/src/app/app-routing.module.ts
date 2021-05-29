import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { LoginComponent } from "./feature/login/components/login.component";
import { HomeDashboardComponent } from "./feature/home-dashboard/home-dashboard.component";
import { DashboardGroupleaderComponent } from "./feature/dashboard-groupleader/dashboard-groupleader.component";
import { AuthGuard } from "./core/guards/auth.guard";
import { GroupLeaderAuthGuard } from "./core/guards/group-leader-auth.guard";
import { DashboardSupervisorComponent } from "./feature/dashboard-supervisor/dashboard-supervisor.component";
import { SupervisorAuthGuard } from "./core/guards/supervisor-auth-guard.service";
import { ClassroomAvailabilityComponent } from "./feature/classroom-availability/classroom-availability.component";
import { SitesListComponent } from "./feature/sites-list/sites-list.component";
import { AdminAuthGuard } from "./core/guards/admin-auth-guard";
import { BuildingsListComponent } from "./feature/buildings-list/buildings-list.component";

const routes: Routes = [
  {
    path: "",
    component: HomeDashboardComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "login",
    component: LoginComponent,
  },
  {
    path: "groupleader",
    component: DashboardGroupleaderComponent,
    canActivate: [AuthGuard, GroupLeaderAuthGuard],
  },
  {
    path: "supervisor/booking-requests",
    component: DashboardSupervisorComponent,
    canActivate: [AuthGuard, SupervisorAuthGuard],
  },
  {
    path: "supervisor/classroom-availability",
    component: ClassroomAvailabilityComponent,
    canActivate: [AuthGuard, SupervisorAuthGuard],
  },
  {
    path: "admin/sites",
    component: SitesListComponent,
    canActivate: [AuthGuard, AdminAuthGuard],
  },
  {
    path: "admin/buildings",
    component: BuildingsListComponent,
    canActivate: [AuthGuard, AdminAuthGuard],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { relativeLinkResolution: "legacy" })],
  exports: [RouterModule],
})
export class AppRoutingModule {}
