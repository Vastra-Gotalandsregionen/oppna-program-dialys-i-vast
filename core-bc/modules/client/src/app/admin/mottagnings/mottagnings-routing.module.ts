import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MottagningsListComponent} from "./mottagnings-list/mottagnings-list.component";

const routes: Routes = [
  {
    path: '',
    component: MottagningsListComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MottagningsRoutingModule { }
