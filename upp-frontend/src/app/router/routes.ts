import { RegisterComponent } from './../components/account/register/register.component';
import { LoginComponent } from './../components/account/login/login.component';
import { ProfileComponent } from './../components/user/profile/profile.component';
import { HomeComponent } from './../components/home/home.component';
import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: '',
        component: HomeComponent,
        // canActivate: []
    },
    {
        path: 'login',
        component: LoginComponent,
        // canActivate: []
    },
    {
        path: 'register',
        component: RegisterComponent,
        // canActivate: []
    },
    {
        path: 'profile',
        component: ProfileComponent,
        // canActivate: []
    }
]