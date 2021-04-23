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



@NgModule({
    declarations: [LoginComponent, BookingRequestCreationByClassroomComponent, DashboardGroupleaderComponent, MenuComponent, HomeDashboardComponent, ToatsComponent, PendingRequestListComponent, CancelledRequestListComponent, ApprovedRequestListComponent, RejectedRequestListComponent],
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
        NgbToastModule
    ]
})
export class FeatureModule { }
