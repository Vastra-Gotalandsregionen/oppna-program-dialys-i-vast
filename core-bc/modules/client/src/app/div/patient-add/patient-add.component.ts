import {Component, ViewChild} from '@angular/core';
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

  location: Location;

  constructor(route: ActivatedRoute,
              http: JwtHttp,
              authService: AuthService) {
    super(route, http, authService);
  }

}
