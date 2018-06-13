import {PatientsComponent} from './patients/patients.component';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PatientAddRequisitionComponent} from './patient-add-requisition/patient-add-requisition.component';
import {PatientEditComponent} from "./patient-edit/patient-edit.component";
import {PatientOrderDetailComponent} from "./patient-order-detail/patient-order-detail.component";
import {PatientDetailComponent} from "./patient-detail/patient-detail.component";
import {RequisitionViewComponent} from "./requisition-view/requisition-view.component";
import {BestInfoEditComponent} from "./best-info-edit/best-info-edit.component";

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
        /*path: ':id/order/:id',
        component: PatientOrderDetailComponent*/
        path: ':patientId/order/:bestInfoId',
        component: BestInfoEditComponent
      }, {
        path: ':id/add-requisition',
        component: PatientAddRequisitionComponent
      }, {
        path: ':id/requisitionview',
        component: RequisitionViewComponent
      }, {
        path: ':id/requisition/:editId',
        component: PatientAddRequisitionComponent
      }, {
        path: ':patientId/add-order',
        component: BestInfoEditComponent
        /*        path: ':id/add-order',
                component: PatientAddOrderComponent*/
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
