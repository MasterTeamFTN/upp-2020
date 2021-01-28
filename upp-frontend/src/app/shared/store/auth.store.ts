import { EntityState, EntityStore, StoreConfig } from '@datorama/akita';
import { User } from 'src/app/model/User';
import { Injectable } from "@angular/core";

export interface AuthState extends EntityState<User> {
    user: Partial<User>,
    token: string
}

export function createInitialState(): AuthState {
    return {
        user: null,
        token: null
    }
}

@Injectable()
@StoreConfig({ name: 'auth' })
export class AuthStore extends EntityStore<AuthState, User> {
    constructor() {
        super(createInitialState());
    }

}