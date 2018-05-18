import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ArtikelsListComponent} from './artikels-list/artikels-list.component';
import {ArtikelsRoutingModule} from "./artikels-routing.module";
import {MatAccordion, MatExpansionModule} from "@angular/material";
import {FlexLayoutModule} from "@angular/flex-layout";
import {SharedModule} from "../../shared/shared.module";
import { GruppMoveComponent } from './grupp-move/grupp-move.component';
import { ArtikelMoveComponent } from './artikel-move/artikel-move.component';
import {ArtikelEditComponent} from "./artikel-edit/artikel-edit.component";

@NgModule({
  imports: [
    CommonModule,
    ArtikelsRoutingModule,
    SharedModule,
    FlexLayoutModule
  ],
  declarations: [ArtikelsListComponent, GruppMoveComponent, ArtikelMoveComponent, ArtikelEditComponent],
  entryComponents: [GruppMoveComponent, ArtikelMoveComponent, ArtikelEditComponent],
  schemas: [
    MatAccordion,
    MatExpansionModule
  ]
})
export class ArtikelsModule {

}
