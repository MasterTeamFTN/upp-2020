import { AccountConfirmationComponent } from './../components/account/account-confirmation/account-confirmation.component';
import { MembershipRequestComponent } from './../components/account/membership-request/membership-request.component';
import { RegisterComponent } from './../components/account/register/register.component';
import { LoginComponent } from './../components/account/login/login.component';
import { ProfileComponent } from './../components/user/profile/profile.component';
import { HomeComponent } from './../components/home/home.component';
import { Routes } from '@angular/router';
import { StartRegistrationComponent } from '../components/registration/start-registration/start-registration.component';
import { LoadFileComponent } from '../components/common/load-file/load-file.component';
import { LoggedInGuard } from './guards/logged-in.guard';
import { WriterGuard } from './guards/writer.guard';
import { PlagiarismComponent } from '../components/user/plagiarism-page/plagiarism.component';

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
        path: 'registerAsWriter',
        component: RegisterComponent,
        // canActivate: []
    },
    {
        path: 'fileUpload',
        component: LoadFileComponent,
        canActivate: [WriterGuard]
    },
    {
        path: 'profile',
        component: ProfileComponent,
        // canActivate: []
    },
    {
        path: 'plagiarism',
        component: PlagiarismComponent,
        // canActivate: []
    },
    {
        path: 'verify',
        component: AccountConfirmationComponent,
        // canActivate: []
    },
    {
        path: 'membershipRequest',
        component: MembershipRequestComponent,
        // canActivate: [LoggedInGuard]
    }
]
