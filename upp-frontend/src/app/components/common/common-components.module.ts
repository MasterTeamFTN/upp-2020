import { CoreModule } from './../../shared/modules/core.module';
import { SnackbarComponent } from './snackbar/snackbar.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';



@NgModule({
  declarations: [SnackbarComponent],
  imports: [
    CommonModule,
    CoreModule
  ]
})
export class CommonComponentsModule { }
