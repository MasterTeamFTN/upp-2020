import { QueryEntity } from '@datorama/akita';
import { Injectable } from '@angular/core';
import { AuthStore, AuthState } from './auth.store';
import { User } from 'src/app/model/User';

@Injectable()
export class AuthQuery extends QueryEntity<AuthState, User> {
  constructor(protected store: AuthStore) {
    super(store);
  }

  user$ = this.select(state => state.user);
  token$ = this.select(state => state.token);
  taskId$ = this.select(state => state.taskId);
  isLoggedIn$ = this.select(state => !!state.token);
  processId$ = this.select(state => state.processId);
  loginTitle$ = this.select(state => state.loginTitle);
  registrationRole$ = this.select(state => state.registrationRole);
  role$ = this.select(state => state.user ? state.user.authority : null);
  
  isMultipartFileRequest$ = this.select(state => state.isMultipartFileRequest);

  isEditor$ = this.select(state => state.user ? state.user.authority === "ROLE_EDITOR" : null);
  isWriter$ = this.select(state => state.user ? state.user.authority === "ROLE_WRITER" : null);
  isReader$ = this.select(state => state.user ? state.user.authority === "ROLE_READER" : null);
  isLecturer$ = this.select(state => state.user ? state.user.authority === "ROLE_LECTURER" : null);
  isChiefEditor$ = this.select(state => state.user ? state.user.authority === "ROLE_CHIEF_EDITOR" : null);
  isBoardMember$ = this.select(state => state.user ? state.user.authority === "ROLE_BOARD_MEMBER" : null);
}
