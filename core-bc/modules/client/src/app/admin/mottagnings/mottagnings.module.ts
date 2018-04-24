import {NgModule} from '@angular/core';
import {SharedModule} from '../../shared/shared.module';
import {MottagningsListComponent} from "./mottagnings-list/mottagnings-list.component";
import {MottagningsRoutingModule} from "./mottagnings-routing.module";

@NgModule({
  imports: [
    SharedModule,
    //MottagningsListComponent,
    MottagningsRoutingModule
  ],
  declarations: [MottagningsListComponent]
})
export class MottagningsModule {
}
