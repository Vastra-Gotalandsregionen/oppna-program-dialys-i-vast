import {NgModule} from '@angular/core';
import {SharedModule} from '../../shared/shared.module';
import {MottagningsListComponent} from "./mottagnings-list/mottagnings-list.component";
import {MottagningsRoutingModule} from "./mottagnings-routing.module";
import {MottagningsDialogComponent} from "./mottagnings-dialog/mottagnings-dialog.component";

@NgModule({
  imports: [
    SharedModule,
    MottagningsRoutingModule
  ],
  entryComponents: [MottagningsDialogComponent],
  declarations: [MottagningsListComponent, MottagningsDialogComponent]
})
export class MottagningsModule {

}
