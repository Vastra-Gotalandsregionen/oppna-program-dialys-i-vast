import {HomeComponent} from './home/home.component';
import {NgModule} from '@angular/core';
import {PreloadAllModules, RouterModule, Routes} from '@angular/router';
import {UserLoggedInGuard} from "./div/guard/user-logged-in.guard";

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'home',
        component: HomeComponent
      },
      {
        path: 'patienter',
        loadChildren: './div/dialys.module#DialysModule',
        canActivate: [UserLoggedInGuard]
      },
      {
        path: 'admin',
        loadChildren: './admin/admin.module#AdminModule',
        canActivate: [UserLoggedInGuard]
      },
      {
        path: 'report',
        loadChildren: './report/report.module#ReportModule',
        canActivate: [UserLoggedInGuard]
      },
      {
        path: '**',
        redirectTo: 'home'
      }
    ]
  }

];

@NgModule({
  imports: [
    RouterModule.forRoot(
      routes, {
        preloadingStrategy: PreloadAllModules, enableTracing: false
      })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
