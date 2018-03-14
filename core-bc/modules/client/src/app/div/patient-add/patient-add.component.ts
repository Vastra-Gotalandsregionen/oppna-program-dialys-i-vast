import {Component, ViewChild} from '@angular/core';
import {ApkFormComponent} from "../apk-form/apk-form.component";
import {PatientDetailComponent} from "../patient-detail/patient-detail.component";
import {AuthService} from "../../core/auth/auth.service";
import {ActivatedRoute} from "@angular/router";
import {JwtHttp} from "../../core/jwt-http";

@Component({
  selector: 'app-patient-add',
  templateUrl: './patient-add.component.html',
  styleUrls: ['./patient-add.component.css'],
})
export class PatientAddComponent extends PatientDetailComponent{

  @ViewChild(ApkFormComponent) apkFormComponent: ApkFormComponent;

  location: Location;

  constructor(route: ActivatedRoute,
              http: JwtHttp,
              authService: AuthService) {
    super(route, http, authService);
  }

}
