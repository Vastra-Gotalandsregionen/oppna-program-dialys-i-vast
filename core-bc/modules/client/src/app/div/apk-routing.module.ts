import {ApkComponent} from './patients/patients.component';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PatientDetailComponent} from './patient-detail/patient-detail.component';
import {ApkEditComponent} from './apk-edit/apk-edit.component';
import {ApkCreateComponent} from './apk-create/apk-create.component';
import {ArchivedDatasComponent} from './archived-datas/archived-datas.component';
import {FormChangedGuard} from "./guard/form-changed.guard";

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
        component: ApkCreateComponent,
        canDeactivate: [FormChangedGuard]
      },
      {
        path: ':id',
        component: PatientDetailComponent
      },
      {
        path: ':id/edit',
        component: ApkEditComponent,
        canDeactivate: [FormChangedGuard]
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
