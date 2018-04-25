import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ArtikelsListComponent} from "./artikels-list/artikels-list.component";

const routes: Routes = [
  {
    path: '',
    component: ArtikelsListComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ArtikelsRoutingModule { }
