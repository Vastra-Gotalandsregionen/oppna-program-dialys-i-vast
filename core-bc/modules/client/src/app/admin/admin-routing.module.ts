import {NgModule} from '@angular/core';
import {AdminComponent} from './admin.component'
import {AdminLandingComponent} from './admin-landing/admin-landing.component'
import {RouterModule, Routes} from '@angular/router';
import {AdminGuard} from "../core/guard/admin.guard";
import {MottagningsModule} from "./mottagnings/mottagnings.module";

const routes: Routes = [
  {
    path: '',
    component: AdminComponent,
    children: [
      {
        path: 'landing',
        component: AdminLandingComponent
      },
      {
        path: 'users',
        canActivate: [AdminGuard],
        loadChildren: './users/users.module#UsersModule'
      },
      {
        path: 'mottagnings',
        canActivate: [AdminGuard],
          //component: MottagningsModule,
        loadChildren: './mottagnings/mottagnings.module#MottagningsModule'
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
  // entryComponents: [MottagningsModule]
})
export class AdminRoutingModule { }
