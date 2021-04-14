import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/components/login.component';
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";



@NgModule({
    declarations: [LoginComponent],
    exports: [
        LoginComponent
    ],
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule
  ]
})
export class FeatureModule { }
