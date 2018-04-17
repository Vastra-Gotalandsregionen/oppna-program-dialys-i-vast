import {NgModule} from '@angular/core';
import {AdminComponent} from './admin.component'
import {AdminLandingComponent} from './admin-landing/admin-landing.component'
import {RouterModule, Routes} from '@angular/router';
import {AdminGuard} from "../core/guard/admin.guard";

const routes: Routes = [
  {
    path: '',
    component: AdminComponent,
    children: [
      {
        path: 'landing',
        component: AdminLandingComponent,
      },
      {
        path: 'users',
        canActivate: [AdminGuard],
        loadChildren: './users/users.module#UsersModule'
      },
      {
        path: '**',
        redirectTo: 'landing'
      },

    ]
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
