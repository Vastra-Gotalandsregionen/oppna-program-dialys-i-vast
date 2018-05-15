import {PatientsComponent} from './patients/patients.component';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PatientAddOrderComponent} from './patient-add-order/patient-add-order.component';
import {PatientAddRequisitionComponent} from './patient-add-requisition/patient-add-requisition.component';
import {PatientEditComponent} from "./patient-edit/patient-edit.component";
import {PatientOrderDetailComponent} from "./patient-order-detail/patient-order-detail.component";
import {PatientDetailComponent} from "./patient-detail/patient-detail.component";

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: '',
        component: PatientsComponent
      },
      {
        path: ':id',
        component: PatientDetailComponent
      },
      {
        path: ':id/order/:id',
        component: PatientOrderDetailComponent
      }, {
        path: ':id/add-requisition',
        component: PatientAddRequisitionComponent
      }, {
        path: ':id/requisition/:editId',
        component: PatientAddRequisitionComponent
      }, {
        path: ':id/add-order',
        component: PatientAddOrderComponent
        //canDeactivate: [FormChangedGuard]
      }, {
        path: ':id/edit',
        component: PatientEditComponent
      }, {
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
export class DialysRoutingModule {
}
