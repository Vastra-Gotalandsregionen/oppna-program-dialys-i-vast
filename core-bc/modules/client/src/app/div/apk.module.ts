import {NgModule} from '@angular/core';
import {ApkComponent} from './patients/patients.component';
import {PatientDetailComponent} from './patient-detail/patient-detail.component';
import {PatientAddOrderComponent} from './patient-add-order/patient-add-order.component';
import {PatientAddRequisitionComponent} from './patient-add-requisition/patient-add-requisition.component';
import {ApkRoutingModule} from './apk-routing.module';
import {SharedModule} from '../shared/shared.module';
import {UserHasDataPermissionGuard} from './guard/user-has-data-permission.guard';
import { ArchivedDatasComponent } from './archived-datas/archived-datas.component';
import {PatientAddComponent} from './patient-add/patient-add.component';
import { PatientEditComponent } from './patient-edit/patient-edit.component';
import { PrescriptionDetailComponent } from './prescription-detail/prescription-detail.component';
import { RequisitionEditComponent } from './requisition-edit/requisition-edit.component';

@NgModule({
  imports: [
    ApkRoutingModule,
    SharedModule
  ],
  declarations: [
    ApkComponent,
    PatientDetailComponent,
    PatientAddComponent,
    PatientAddOrderComponent,
    PatientAddRequisitionComponent,
    ArchivedDatasComponent,
    PatientEditComponent,
    PrescriptionDetailComponent,
    RequisitionEditComponent
  ],
  providers: [
    UserHasDataPermissionGuard
  ]
})
export class ApkModule { }
