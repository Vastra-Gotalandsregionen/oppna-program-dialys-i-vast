import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UsersListComponent} from './users-list/users-list.component';
import {UserEditComponent} from './user-edit/user-edit.component';
import {UserCreateComponent} from './user-create/user-create.component';
import {AdminGuard} from "../../core/guard/admin.guard";

const routes: Routes = [
  {
    path: '',
    component: UsersListComponent,
  },
  {
    path: ':userName/edit',
    component: UserEditComponent,
  },
  {
    path: 'create',
    component: UserCreateComponent,
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule], providers:[AdminGuard]
})
export class UsersRoutingModule {
}
