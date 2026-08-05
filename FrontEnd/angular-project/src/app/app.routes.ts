import { Routes } from '@angular/router';
import { Home } from './home/home';
import { NotFound } from './not-found/not-found';
import { loginGuard } from './guards/login-guard';

export const routes: Routes = [
  { path: '', component: Home, pathMatch: 'full' },
  {
    path: 'login',
    loadComponent: () => import('./login/login').then((m) => m.Login),
    canActivate: [loginGuard],
  },
  { path: '404', component: NotFound },
  { path: '**', redirectTo: '', pathMatch: 'full' },
];
