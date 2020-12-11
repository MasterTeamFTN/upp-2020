import { NgModule } from '@angular/core';
import { DatePipe } from '@angular/common';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MaterialModule } from './material.module';
import { TranslateModule } from '@ngx-translate/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';


/**
 * Ovde ce se nalaziti moduli koji su neophodni a nalaze se unutar angular core.
 */
@NgModule({
  declarations: [],
  imports: [
    TranslateModule,
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    MaterialModule,
    RouterModule,
  ],
  exports: [
    TranslateModule,
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    MaterialModule,
    RouterModule,
  ],
  providers: [DatePipe]
})
export class CoreModule { }
