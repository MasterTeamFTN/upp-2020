import { UserModule } from './../../components/user/user.module';
import { AccountModule } from './../../components/account/account.module';

import { NgModule } from '@angular/core';


/*
* Ovde se nalaze moduli koji grupisu osnovne grupe modula
*/
@NgModule({
  declarations: [],
  imports: [
    UserModule,
    AccountModule
    // ovde ce biti Home, Admin, User module
  ],
  exports: []
})
export class MainModule { }
