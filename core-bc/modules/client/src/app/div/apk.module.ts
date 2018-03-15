import {NgModule} from '@angular/core';
import {ApkComponent} from './patients/patients.component';
import {PatientDetailComponent} from './patient-detail/patient-detail.component';
import {PatientAddOrderComponent} from './patient-add-order/patient-add-order.component';
import {PatientAddRequisitionComponent} from './patient-add-requisition/patient-add-requisition.component';
import {ApkEditComponent} from './apk-edit/apk-edit.component';
import {ApkFormComponent} from './apk-form/apk-form.component';
import {ApkRoutingModule} from './apk-routing.module';
import {SharedModule} from '../shared/shared.module';
import {UserHasDataPermissionGuard} from './guard/user-has-data-permission.guard';
import { ArchivedDatasComponent } from './archived-datas/archived-datas.component';
import {FormChangedGuard} from "./guard/form-changed.guard";
import {PatientAddComponent} from './patient-add/patient-add.component';
import { PatientEditComponent } from './patient-edit/patient-edit.component';
import { PrescriptionDetailComponent } from './prescription-detail/prescription-detail.component';

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
    ApkEditComponent,
    ApkFormComponent,
    ArchivedDatasComponent,
    PatientEditComponent,
    PrescriptionDetailComponent
  ],
  providers: [
    UserHasDataPermissionGuard,
    FormChangedGuard
  ]
})
export class ApkModule { }
