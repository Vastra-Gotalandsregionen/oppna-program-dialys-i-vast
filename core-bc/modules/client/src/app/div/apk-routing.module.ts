import {ApkComponent} from './patients/patients.component';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PatientDetailComponent} from './patient-detail/patient-detail.component';
import {PatientAddOrderComponent} from './patient-add-order/patient-add-order.component';
import {PatientAddRequisitionComponent} from './patient-add-requisition/patient-add-requisition.component';
import {ApkEditComponent} from './apk-edit/apk-edit.component';
import {ArchivedDatasComponent} from './archived-datas/archived-datas.component';
import {FormChangedGuard} from "./guard/form-changed.guard";
import {PatientAddComponent} from './patient-add/patient-add.component';
import {PatientEditComponent} from "./patient-edit/patient-edit.component";
import {PrescriptionDetailComponent} from "./prescription-detail/prescription-detail.component";

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: '',
        component: ApkComponent
      },
      {
        path: 'create',
        component: PatientAddComponent
        //canDeactivate: [FormChangedGuard]
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
      },

      {
        path: ':id/add-order',
        component: PatientAddOrderComponent
        //canDeactivate: [FormChangedGuard]
      },


      {
        path: ':id/edit',
        component: PatientEditComponent
        //component: ApkEditComponent
        //,        canDeactivate: [FormChangedGuard]
      },
      {
        path: ':id/archivedDatas',
        component: ArchivedDatasComponent
      }

    ]
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApkRoutingModule { }
