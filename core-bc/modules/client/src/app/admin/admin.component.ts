import {Component, OnInit} from '@angular/core';
import {StateService} from '../core/state/state.service';
import {AuthService} from '../core/auth/auth.service';

import {FlikRot} from "../model/FlikRot";
import {JwtHttp} from "../core/jwt-http";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  public flikRots: FlikRot[];

  constructor(private authService: AuthService,
              private stateService: StateService,
              private http: JwtHttp) {
  }

  ngOnInit() {
    if(this.authService.isAdmin()) {
      this.http.get('/api/flikrot/').map(response => response.json()).subscribe(
        (flikRot: FlikRot[]) => {
          this.flikRots = flikRot;
        }
      )
    }
  }

  get admin() {
    return this.authService.isAdmin();
  }

  get loggedIn() {
    return this.authService.getLoggedInUserId() ? true : false;
  }

  get sidebarOpen(): boolean {
    return this.stateService.showSidenav;
  }

}
