import { SnackbarComponent } from 'src/app/components/common/snackbar/snackbar.component';
import { UserModule } from './../../components/user/user.module';
import { AccountModule } from './../../components/account/account.module';

import { NgModule } from '@angular/core';
import { MAT_SNACK_BAR_DEFAULT_OPTIONS } from '@angular/material';
import { GenericFormComponent } from 'src/app/components/common/generic-form/generic-form.component';
import { RegistrationModule } from 'src/app/components/registration/registration.module';


/*
* Ovde se nalaze moduli koji grupisu osnovne grupe modula
*/
@NgModule({
  declarations: [SnackbarComponent],
  entryComponents: [SnackbarComponent],
  imports: [
    UserModule,
    AccountModule,
    RegistrationModule
    // ovde ce biti Home, Admin, User module
  ],
  exports: [],
  providers: [{provide: MAT_SNACK_BAR_DEFAULT_OPTIONS, useValue: {duration: 2000, horizontalPosition: "right", verticalPosition: "bottom"},}
]
})
export class MainModule { }
