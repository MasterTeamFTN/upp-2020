import { CommonStoreModule } from './../../shared/store/common-store.module';
import { AuthStore } from './../../shared/store/auth.store';
import { CoreModule } from './../../shared/modules/core.module';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent
  ],
  imports: [
    CoreModule,
    CommonModule,
    CommonStoreModule
  ],
  exports: [
    LoginComponent,
    RegisterComponent
  ],
  // providers: [AuthStore]
})
export class AccountModule { }
