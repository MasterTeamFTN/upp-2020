import { SnackbarComponent } from 'src/app/components/common/snackbar/snackbar.component';
import { UserModule } from './../../components/user/user.module';
import { AccountModule } from './../../components/account/account.module';

import { NgModule } from '@angular/core';
import { MAT_SNACK_BAR_DEFAULT_OPTIONS } from '@angular/material';
import { GenericFormComponent } from 'src/app/components/common/generic-form/generic-form.component';


/*
* Ovde se nalaze moduli koji grupisu osnovne grupe modula
*/
@NgModule({
  declarations: [SnackbarComponent],
  entryComponents: [SnackbarComponent],
  imports: [
    UserModule,
    AccountModule
    // ovde ce biti Home, Admin, User module
  ],
  exports: [],
  providers: [    {provide: MAT_SNACK_BAR_DEFAULT_OPTIONS, useValue: {duration: 5000, horizontalPosition: "right", verticalPosition: "bottom"},}
]
})
export class MainModule { }
