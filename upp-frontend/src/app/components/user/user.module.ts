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
<<<<<<< HEAD
import { PlagiarismComponent } from './plagiarism-page/plagiarism.component';
=======
import { AccountModule } from '../account/account.module';
<<<<<<< HEAD
>>>>>>> main
=======
import { ChiefEditorHomeComponent } from './chief-editor-home/chief-editor-home.component';
import { AddReviewDialog } from './chief-editor-home/dialog/add-review-dialog';
import { BetaReaderDialog } from './reader-home/dialog/beta-reader-dialog';
import { LecturerDialog } from './lecturer-home/dialog/lecturer-dialog';
>>>>>>> main



@NgModule({
  declarations: [ProfileComponent, WriterHomeComponent, ReaderHomeComponent, LecturerHomeComponent, BoardMemberHomeComponent, PlagiarismComponent],
=======
  declarations: [ProfileComponent,
    WriterHomeComponent,
    ReaderHomeComponent,
    LecturerHomeComponent,
    BoardMemberHomeComponent,
    ChiefEditorHomeComponent,
    AddReviewDialog,
    BookDataDialog,
    BetaReaderDialog,
    LecturerDialog],
>>>>>>> main
  imports: [
    CommonModule,
    CoreModule,
    CommonComponentsModule,
    AccountModule,
  ],
  entryComponents: [AddReviewDialog, BookDataDialog, BetaReaderDialog, LecturerDialog],
  exports: [WriterHomeComponent, ReaderHomeComponent, LecturerHomeComponent, BoardMemberHomeComponent, ChiefEditorHomeComponent]
})
export class UserModule { }
