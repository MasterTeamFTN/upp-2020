import { CoreModule } from './../../shared/modules/core.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfileComponent } from './profile/profile.component';
import { WriterHomeComponent } from './writer-home/writer-home.component';
import { ReaderHomeComponent } from './reader-home/reader-home.component';
import { LecturerHomeComponent } from './lecturer-home/lecturer-home.component';
import { CommonComponentsModule } from '../common/common-components.module';



@NgModule({
  declarations: [ProfileComponent, WriterHomeComponent, ReaderHomeComponent, LecturerHomeComponent],
  imports: [
    CommonModule,
    CoreModule,
    CommonComponentsModule
  ],
  exports:[WriterHomeComponent, ReaderHomeComponent, LecturerHomeComponent]
})
export class UserModule { }
