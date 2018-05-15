import {NgModule} from '@angular/core';
import {UsersListComponent} from './users-list/users-list.component';
import {SharedModule} from '../../shared/shared.module';
import {UsersRoutingModule} from './users-routing.module';
import { UserEditComponent } from './user-edit/user-edit.component';
import { UserCreateComponent } from './user-create/user-create.component';
import { UserFormComponent } from './user-form/user-form.component';
import {FlexLayoutModule} from "@angular/flex-layout";

@NgModule({
  imports: [
    SharedModule,
    UsersRoutingModule,
    FlexLayoutModule
  ],
  declarations: [UsersListComponent, UserEditComponent, UserCreateComponent, UserFormComponent]
})
export class UsersModule { }
