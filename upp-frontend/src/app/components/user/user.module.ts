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
import { AddReviewDialog } from './chief-editor-home/dialog/add-review-dialog';
import { BetaReaderDialog } from './reader-home/dialog/beta-reader-dialog';
import { LecturerDialog } from './lecturer-home/dialog/lecturer-dialog';
import { EditorHomeComponent } from './editor-home/editor-home.component';



@NgModule({
  declarations: [ProfileComponent,
    WriterHomeComponent,
    ReaderHomeComponent,
    LecturerHomeComponent,
    BoardMemberHomeComponent,
    ChiefEditorHomeComponent,
    AddReviewDialog,
    BookDataDialog,
    BetaReaderDialog,
    LecturerDialog,
    EditorHomeComponent],
  imports: [
    CommonModule,
    CoreModule,
    CommonComponentsModule,
    AccountModule,
  ],
  entryComponents: [AddReviewDialog, BookDataDialog, BetaReaderDialog, LecturerDialog],
  exports: [WriterHomeComponent, ReaderHomeComponent, LecturerHomeComponent, BoardMemberHomeComponent, ChiefEditorHomeComponent, EditorHomeComponent]
})
export class UserModule { }
