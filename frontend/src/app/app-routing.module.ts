import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from "./feature/login/components/login.component";
import {HomeDashboardComponent} from "./feature/home-dashboard/home-dashboard.component";
import {DashboardGroupleaderComponent} from "./feature/dashboard-groupleader/dashboard-groupleader.component";
import {AuthGuard} from "./core/guards/auth.guard";
import {GroupLeaderAuthGuard} from "./core/guards/group-leader-auth.guard";

const routes: Routes = [
  {
    path: '',
    component: HomeDashboardComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'groupleader',
    component: DashboardGroupleaderComponent,
    canActivate: [AuthGuard, GroupLeaderAuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
