import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/shared/modules/material.module';
import { StartRegistrationComponent } from './start-registration/start-registration.component';



@NgModule({
  declarations: [StartRegistrationComponent],
  imports: [
    CommonModule,
    MaterialModule
  ]
})
export class ReservationModule { }
