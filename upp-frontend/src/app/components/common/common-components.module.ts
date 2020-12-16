import { CoreModule } from './../../shared/modules/core.module';
import { SnackbarComponent } from './snackbar/snackbar.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GenericFormComponent } from './generic-form/generic-form.component';



@NgModule({
  imports: [
    CommonModule,
    CoreModule
  ],
  declarations: []
})
export class CommonComponentsModule { }
