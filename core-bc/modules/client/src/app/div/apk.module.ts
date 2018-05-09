import {NgModule} from '@angular/core';
import {ApkComponent} from './patients/patients.component';
import {PatientAddOrderComponent} from './patient-add-order/patient-add-order.component';
import {PatientAddRequisitionComponent} from './patient-add-requisition/patient-add-requisition.component';
import {ApkRoutingModule} from './apk-routing.module';
import {SharedModule} from '../shared/shared.module';
import {UserHasDataPermissionGuard} from './guard/user-has-data-permission.guard';
import { PatientEditComponent } from './patient-edit/patient-edit.component';
import { PatientOrderDetailComponent } from './patient-order-detail/patient-order-detail.component';
import { RequisitionEditComponent } from './requisition-edit/requisition-edit.component';
import { FlexLayoutModule} from "@angular/flex-layout";
import {PatientDetailComponent} from "./patient-detail/patient-detail.component";
import { PatientAddRequisitionSaveDialogComponent } from './patient-add-requisition-save-dialog/patient-add-requisition-save-dialog.component';

@NgModule({
  imports: [
    ApkRoutingModule,
    SharedModule,

    FlexLayoutModule
  ],
  declarations: [
    ApkComponent,
    PatientAddOrderComponent,
    PatientAddRequisitionComponent,
    PatientDetailComponent,
    PatientEditComponent,
    PatientOrderDetailComponent,
    RequisitionEditComponent,
    PatientAddRequisitionSaveDialogComponent
  ],
  providers: [
    UserHasDataPermissionGuard
  ],
  entryComponents: [PatientAddRequisitionSaveDialogComponent]
})
export class ApkModule { }
