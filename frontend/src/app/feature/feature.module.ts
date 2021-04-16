import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/components/login.component';
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import { BookingRequestCreationByClassroomComponent } from './booking-request/booking-request-creation-by-classroom/booking-request-creation-by-classroom.component';



@NgModule({
    declarations: [LoginComponent, BookingRequestCreationByClassroomComponent],
    exports: [
        LoginComponent,
        BookingRequestCreationByClassroomComponent
    ],
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule
  ]
})
export class FeatureModule { }
