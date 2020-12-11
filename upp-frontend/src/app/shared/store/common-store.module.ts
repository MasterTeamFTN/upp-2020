import { AuthQuery } from './auth.query';
import { AuthService } from './../services/auth/auth.service';
import { AuthStore } from './auth.store';
import { NgModule } from '@angular/core';
import { persistState } from '@datorama/akita';

/*
* all stores stated in include
* will be persisted in localStorage
*/
persistState({
  include: [
    'auth'
  ]
});

@NgModule({
  providers: [
    AuthStore,
    AuthService,
    AuthQuery
  ],
  declarations: [],
  imports: []
})
export class CommonStoreModule { }
