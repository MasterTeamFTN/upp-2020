import { CoreModule } from './../../shared/modules/core.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfileComponent } from './profile/profile.component';
import { WriterHomeComponent } from './writer-home/writer-home.component';
import { ReaderHomeComponent } from './reader-home/reader-home.component';
import { LecturerHomeComponent } from './lecturer-home/lecturer-home.component';
import { CommonComponentsModule } from '../common/common-components.module';
import { BoardMemberHomeComponent } from './board-member-home/board-member-home.component';
<<<<<<< HEAD
import { PlagiarismComponent } from './plagiarism-page/plagiarism.component';
=======
import { AccountModule } from '../account/account.module';
>>>>>>> main



@NgModule({
  declarations: [ProfileComponent, WriterHomeComponent, ReaderHomeComponent, LecturerHomeComponent, BoardMemberHomeComponent, PlagiarismComponent],
  imports: [
    CommonModule,
    CoreModule,
    CommonComponentsModule,
    AccountModule,
  ],
  exports:[WriterHomeComponent, ReaderHomeComponent, LecturerHomeComponent, BoardMemberHomeComponent]
})
export class UserModule { }
