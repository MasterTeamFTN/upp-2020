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
  isLoggedIn$ = this.select(state => !!state.token);
  isEditor$ = this.select(state => state.user ? state.user.authority === "ROLE_EDITOR" : null);
  role$ = this.select(state => state.user ? state.user.authority : null);
}
