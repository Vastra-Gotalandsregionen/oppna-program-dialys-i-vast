import {NgModule} from '@angular/core';
import {PatientsComponent} from './patients/patients.component';
import {PatientAddOrderComponent} from './patient-add-order/patient-add-order.component';
import {PatientAddRequisitionComponent} from './patient-add-requisition/patient-add-requisition.component';
import {DialysRoutingModule} from './dialys-routing.module';
import {SharedModule} from '../shared/shared.module';
import {UserHasDataPermissionGuard} from './guard/user-has-data-permission.guard';
import { PatientEditComponent } from './patient-edit/patient-edit.component';
import { PatientOrderDetailComponent } from './patient-order-detail/patient-order-detail.component';
import { FlexLayoutModule} from "@angular/flex-layout";
import {PatientDetailComponent} from "./patient-detail/patient-detail.component";
import { PatientAddRequisitionSaveDialogComponent } from './patient-add-requisition-save-dialog/patient-add-requisition-save-dialog.component';
import { RequisitionViewComponent } from './requisition-view/requisition-view.component';
import {RequisitionDataService} from "./services/requisition-data.service";

@NgModule({
  imports: [
    DialysRoutingModule,
    SharedModule,

    FlexLayoutModule
  ],
  declarations: [
    PatientsComponent,
    PatientAddOrderComponent,
    PatientAddRequisitionComponent,
    PatientDetailComponent,
    PatientEditComponent,
    PatientOrderDetailComponent,
    PatientAddRequisitionSaveDialogComponent,
    RequisitionViewComponent
  ],
  providers: [
    UserHasDataPermissionGuard,
    RequisitionDataService
  ],
  entryComponents: [PatientAddRequisitionSaveDialogComponent]
})
export class DialysModule { }
