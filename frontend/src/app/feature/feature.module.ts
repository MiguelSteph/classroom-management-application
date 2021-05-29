import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/components/login.component';
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import { BookingRequestCreationByClassroomComponent } from './booking-request/booking-request-creation-by-classroom/booking-request-creation-by-classroom.component';
import {NgbDatepickerModule, NgbToastModule} from "@ng-bootstrap/ng-bootstrap";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import { DashboardGroupleaderComponent } from './dashboard-groupleader/dashboard-groupleader.component';
import { MenuComponent } from './menu/menu.component';
import { HomeDashboardComponent } from './home-dashboard/home-dashboard.component';
import { ToatsComponent } from './toats/toats.component';
import { PendingRequestListComponent } from './pending-request-list/pending-request-list.component';
import { CancelledRequestListComponent } from './cancelled-request-list/cancelled-request-list.component';
import { ApprovedRequestListComponent } from './approved-request-list/approved-request-list.component';
import { RejectedRequestListComponent } from './rejected-request-list/rejected-request-list.component';
import {RouterModule} from "@angular/router";
import { DashboardSupervisorComponent } from './dashboard-supervisor/dashboard-supervisor.component';
import { BookingRequestDetailComponent } from './booking-request-detail/booking-request-detail.component';
import { ClassroomAvailabilityComponent } from './classroom-availability/classroom-availability.component';
import { NewClassroomAvailabilityComponent } from './new-classroom-availability/new-classroom-availability.component';
import { SitesListComponent } from './sites-list/sites-list.component';
import { NewSiteComponent } from './new-site/new-site.component';
import { BuildingsListComponent } from './buildings-list/buildings-list.component';
import { NewBuildingComponent } from './new-building/new-building.component';
import { ClassroomsListComponent } from './classrooms-list/classrooms-list.component';
import { NewClassroomComponent } from './new-classroom/new-classroom.component';



@NgModule({
    declarations: [LoginComponent, BookingRequestCreationByClassroomComponent, DashboardGroupleaderComponent, MenuComponent, HomeDashboardComponent, ToatsComponent, PendingRequestListComponent, CancelledRequestListComponent, ApprovedRequestListComponent, RejectedRequestListComponent, DashboardSupervisorComponent, BookingRequestDetailComponent, ClassroomAvailabilityComponent, NewClassroomAvailabilityComponent, SitesListComponent, NewSiteComponent, BuildingsListComponent, NewBuildingComponent, ClassroomsListComponent, NewClassroomComponent],
  exports: [
    LoginComponent,
    BookingRequestCreationByClassroomComponent,
    DashboardGroupleaderComponent, MenuComponent, ToatsComponent
  ],
    imports: [
        CommonModule,
        // HttpClientModule,
        FormsModule,
        NgbDatepickerModule,
        FontAwesomeModule,
        NgbToastModule,
        RouterModule
    ]
})
export class FeatureModule { }
