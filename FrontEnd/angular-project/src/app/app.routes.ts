import { Routes } from '@angular/router';
import { Home } from './home/home';
import { Login } from './login/login';
import { NotFound } from './not-found/not-found';

export const routes: Routes = [
  { path: '', component: Home },
  { path: 'login', loadComponent: () => import('./login/login').then((m) => m.Login) },
  { path: '**', component: NotFound },
];
