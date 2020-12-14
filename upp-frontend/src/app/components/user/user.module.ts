import { CoreModule } from './../../shared/modules/core.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfileComponent } from './profile/profile.component';



@NgModule({
  declarations: [ProfileComponent],
  imports: [
    CommonModule,
    CoreModule
  ]
})
export class UserModule { }
