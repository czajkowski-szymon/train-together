import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { DiscoverComponent } from './discover/discover.component';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
    {
        path: "login",
        component: LoginComponent
    },    
    {
        path: "register",
        component: RegistrationComponent
    },
    {
        path: "discover",
        component: DiscoverComponent,
        canActivate: [authGuard]
    },
];
