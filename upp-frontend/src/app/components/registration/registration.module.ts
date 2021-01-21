import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/shared/modules/material.module';
import { StartRegistrationComponent } from './start-registration/start-registration.component';
import { LoadFileComponent } from '../common/load-file/load-file.component';
import { CommonComponentsModule } from '../common/common-components.module';



@NgModule({
  declarations: [StartRegistrationComponent,
    ],
  imports: [
    CommonModule,
    MaterialModule,
    CommonComponentsModule
  ]
})
export class RegistrationModule { }
