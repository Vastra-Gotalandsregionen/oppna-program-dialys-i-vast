import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ArtikelsListComponent } from './artikels-list/artikels-list.component';
import {ArtikelsRoutingModule} from "./artikels-routing.module";

@NgModule({
  imports: [
    CommonModule,
    ArtikelsRoutingModule
  ],
  declarations: [ArtikelsListComponent]
})
export class ArtikelsModule { }
