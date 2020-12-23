import { AccountConfirmationComponent } from './../components/account/account-confirmation/account-confirmation.component';
import { RegisterComponent } from './../components/account/register/register.component';
import { LoginComponent } from './../components/account/login/login.component';
import { ProfileComponent } from './../components/user/profile/profile.component';
import { HomeComponent } from './../components/home/home.component';
import { Routes } from '@angular/router';
import { StartRegistrationComponent } from '../components/registration/start-registration/start-registration.component';

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
        component: StartRegistrationComponent,
        // canActivate: []
    },
    {
        path: 'registerAsReader',
        component: RegisterComponent,
        // canActivate: []
    },
    {
        path: 'profile',
        component: ProfileComponent,
        // canActivate: []
    },
    {
        path: 'verify',
        component: AccountConfirmationComponent,
        // canActivate: []
    }
]