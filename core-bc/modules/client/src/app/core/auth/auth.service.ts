import {Injectable} from '@angular/core';
import {JwtHelper} from 'angular2-jwt/angular2-jwt';
import {Patient} from '../../model/Patient';
import {Router} from '@angular/router';
import {Http} from '@angular/http';
import {Observable} from "rxjs/Observable";
import {Subscription} from "rxjs/Subscription";
import {User} from "../../model/user";

@Injectable()
export class AuthService {

  renewSubscription: Subscription;

  constructor(private jwtHelper: JwtHelper,
              private http: Http,
              private router: Router) {

    const localStorageToken = localStorage.getItem('apkJwtToken');

    if (localStorageToken) {
      this.jwt = localStorageToken;
    }

    if (this.isTokenExpired()) {
      this.resetAuth();
    }

    Observable.interval(10000)
      .subscribe(_ => {
        if (this.isTokenExpired()) {
          this.resetAuth();
        }
      });
  }

  private startRenew() {
    if (this.renewSubscription) {
      this.renewSubscription.unsubscribe();
    }

    this.renewSubscription = Observable.interval(60000)
      .switchMap(() => this.http.post('/api/login/renew', this.jwt))
      .retry(4)
      .subscribe(
        response => this.jwt = response.text(),
        error => {
          this.jwt = null;
          this.renewSubscription.unsubscribe();
        }
      );
  }

  isTokenExpired() {
    const token = this.getToken();
    return token && (token.exp - new Date().getTime() / 1000 < 0);
  }

  getToken(): any {
    const jwtTokenString = this.jwt;
    return jwtTokenString ? this.jwtHelper.decodeToken(jwtTokenString) : null;
  }

  isAuthenticated(): boolean {
    return this.getToken() && !this.isTokenExpired();
  }

  get jwt(): string {
    return localStorage.getItem('apkJwtToken');
  }

  set jwt(value: string) {

    if (value) {

      localStorage.setItem('apkJwtToken', value);

      this.startRenew();
    } else if (this.getToken()) {
      // Logout

      this.router.navigate(['/']);
      localStorage.removeItem('apkJwtToken');
    }

  }

  resetAuth() {
    this.jwt = null;
  }

  getLoggedInUserId(): string {
    const token = this.getToken();
    return token ? token.sub : null;
  }

  getLoggedInDisplayName(): string {
    const token = this.getToken();
    return token ? token.displayName : null;
  }

  getAdmin(): boolean {
    const token = this.getToken();
    return token ? token.admin : false;
  }

  getUser(): User {
    return this.getToken();
  }

  getPharmaceut(): boolean {
    const token = this.getToken();
    return token ? token.pharmaceut : false;
  }

  public getSjukskoterska(): boolean {
    const token = this.getToken();
    return token ? token.sjukskoterska : false;
  }

  isAdmin() {
    /*const token = this.getToken();
    if (token) {
      const roles = <string[]>token.roles;
      return roles.indexOf('admin') > -1;
    }

    return false;*/
    return this.getAdmin();
  }

  canImpersonate() {
    const token = this.getToken();
    if (token) {
      const roles = <string[]>token.roles;
      return roles.indexOf('IMPERSONATE') > -1;
    }

    return false;
  }

  userHasDataEditPermission(data: Patient) {
    if (this.getSjukskoterska() || this.getPharmaceut()) {
      return true;
    }

    // const token = this.getToken();
    /*if (token && token.prodn1s) {
      return token.prodn1s.indexOf(data.prodn1.id) > -1;
    }*/
    return false;
  }

}
