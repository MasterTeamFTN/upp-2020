import { CoreModule } from './../../shared/modules/core.module';
import { SnackbarComponent } from './snackbar/snackbar.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GenericFormComponent } from './generic-form/generic-form.component';
import { LoadFileComponent } from './load-file/load-file.component';



@NgModule({
  imports: [
    CommonModule,
    CoreModule
  ],
  declarations: [GenericFormComponent, LoadFileComponent],
  exports: [GenericFormComponent, LoadFileComponent]
})
export class CommonComponentsModule { }
