import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../core/auth/auth.service";

import {Util} from "../../core/util/util";
import {FlikRot} from "../../model/FlikRot";
import {JwtHttp} from "../../core/jwt-http";

@Component({
  selector: 'app-admin-landing',
  templateUrl: './admin-landing.component.html',
  styleUrls: ['./admin-landing.component.scss']
})
export class AdminLandingComponent implements OnInit {

  public flikRots: FlikRot[];

  constructor(private authService: AuthService, private http: JwtHttp) {}

  ngOnInit() {
    this.http.get('/api/flikrot/').map(response => response.json()).subscribe(
      (flikRot: FlikRot[]) => {
        this.flikRots = flikRot;
      }
    )
  }

  get admin() {
    return this.authService.isAdmin();
  }

  get loggedIn() {
    return this.authService.isAuthenticated();
  }

}
