import { LoggedInGuard } from './guards/logged-in.guard';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { routes } from './routes';
import { BoardMemberGuard } from './guards/board-member.guard';
import { WriterGuard } from './guards/writer.guard';


@NgModule({
  imports: [RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' })],
  exports: [RouterModule],
  providers: [
    LoggedInGuard,
    BoardMemberGuard,
    WriterGuard
    // guards
  ]
})
export class AppRoutingModule { }
