import {ApkComponent} from './patients/patients.component';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PatientAddOrderComponent} from './patient-add-order/patient-add-order.component';
import {PatientAddRequisitionComponent} from './patient-add-requisition/patient-add-requisition.component';
import {PatientEditComponent} from "./patient-edit/patient-edit.component";
import {PrescriptionDetailComponent} from "./prescription-detail/prescription-detail.component";
import {PatientDetailComponent} from "./patient-detail/patient-detail.component";

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: '',
        component: ApkComponent
      },
      {
        path: ':id',
        component: PatientDetailComponent
      },
      {
        path: ':id/prescription/:id',
        component: PrescriptionDetailComponent
      },
      {
        path: ':id/add-requisition',
        component: PatientAddRequisitionComponent
        //canDeactivate: [FormChangedGuard]
      }, {
        path: ':id/add-order',
        component: PatientAddOrderComponent
        //canDeactivate: [FormChangedGuard]
      }, {
        path: ':id/edit',
        component: PatientEditComponent
      },/* {
        path: '/patienter',
        component: ApkComponent
      },*/ {
        path: '/patienter/:id',
        component: PatientDetailComponent
      }, {
        path: 'create/edit/',
        component: PatientEditComponent
      }
    ]
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApkRoutingModule {
}
