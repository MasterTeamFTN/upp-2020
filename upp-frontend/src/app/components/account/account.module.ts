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
import { MembershipRequestComponent } from './membership-request/membership-request.component';
import { WriterPaymentComponent } from './writer-payment/writer-payment.component';


@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent,
    AccountConfirmationComponent,
    MembershipRequestComponent,
    WriterPaymentComponent,
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
    RegisterComponent,
    WriterPaymentComponent
  ],
  // providers: [AuthStore]
})
export class AccountModule { }
