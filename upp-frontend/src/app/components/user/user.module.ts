import { BookDataDialog } from './writer-home/dialog/book-data-dialog';
import { CoreModule } from './../../shared/modules/core.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfileComponent } from './profile/profile.component';
import { WriterHomeComponent } from './writer-home/writer-home.component';
import { ReaderHomeComponent } from './reader-home/reader-home.component';
import { LecturerHomeComponent } from './lecturer-home/lecturer-home.component';
import { CommonComponentsModule } from '../common/common-components.module';
import { BoardMemberHomeComponent } from './board-member-home/board-member-home.component';
import { AccountModule } from '../account/account.module';
import { ChiefEditorHomeComponent } from './chief-editor-home/chief-editor-home.component';
import { AddReviewDialog } from './chief-editor-home/add-review-dialog';



@NgModule({
  declarations: [ProfileComponent, WriterHomeComponent, ReaderHomeComponent, LecturerHomeComponent, BoardMemberHomeComponent, ChiefEditorHomeComponent, AddReviewDialog, BookDataDialog],
  imports: [
    CommonModule,
    CoreModule,
    CommonComponentsModule,
    AccountModule,
  ],
  entryComponents: [AddReviewDialog, BookDataDialog],
  exports:[WriterHomeComponent, ReaderHomeComponent, LecturerHomeComponent, BoardMemberHomeComponent, ChiefEditorHomeComponent]
})
export class UserModule { }
