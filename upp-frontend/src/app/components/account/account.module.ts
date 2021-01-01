import { CommonStoreModule } from './../../shared/store/common-store.module';
import { AuthStore } from './../../shared/store/auth.store';
import { CoreModule } from './../../shared/modules/core.module';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CommonComponentsModule } from '../common/common-components.module';
import { GenericFormComponent } from '../common/generic-form/generic-form.component';
import { MaterialModule } from 'src/app/shared/modules/material.module';
import { AccountConfirmationComponent } from './account-confirmation/account-confirmation.component';


@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent,
    AccountConfirmationComponent,
  ],
  imports: [
    CoreModule,
    CommonModule,
    CommonStoreModule,
    MaterialModule,
    CommonComponentsModule
  ],
  exports: [
    LoginComponent,
    RegisterComponent
  ],
  // providers: [AuthStore]
})
export class AccountModule { }
