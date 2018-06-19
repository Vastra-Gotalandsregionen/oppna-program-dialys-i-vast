import {Component, Input, OnInit} from '@angular/core';
import {ErrorHandler} from '../../../shared/error-handler';
import {User} from '../../../model/user';
import {JwtHttp} from '../../../core/jwt-http';
import {Router} from '@angular/router';
import {Mottagning} from "../../../model/Mottagning";
import {MatSnackBar} from "@angular/material";

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.scss']
})
export class UserFormComponent implements OnInit {

  @Input('userName') userName;

  user: User = new User();

  inCreationMode: boolean = true;
  // If the user selected to make a new user and not edit an existing.
  demandPassword: boolean = true;

  mottagnings: Array<Mottagning> = [];

  userNameClash: boolean = false;
  userStatus: boolean;

  constructor(private http: JwtHttp,
              private snackBar: MatSnackBar,
              private errorHandler: ErrorHandler,
              private router: Router) {
  }

  ngOnInit() {
    if (this.userName) {
      this.inCreationMode = false;
      this.http.get('/api/user/' + this.userName).map(response => response.json()).subscribe(
        (u: User) => {
          this.user = u;
          this.userStatus = this.user.status == 'Aktiv';
          this.userNameChange(null);
        }
      );
    } else {
      this.user = new User();
      this.user.status = 'Aktiv';
      this.userStatus = true;
    }

    this.http.get('/api/mottagning').map(response => response.json()).subscribe(
      (ms: Array<Mottagning>) => {
        this.mottagnings = ms;
      }
    );

  }

  save() {
    this.http.put('/api/user/', this.user)
      .subscribe(result => {
        this.router.navigate(['/admin/users']);
      });
  }

  doesUserHaveMottagning(mottagning: Mottagning): boolean {
    for (const m of this.user.mottagnings) {
      if (m.id === mottagning.id) return true;
    }
    return false;
  }

  onMottagningChecked(mottagning: Mottagning) {
    if (this.doesUserHaveMottagning(mottagning)) {
      var i: number = 0;
      for (const m of this.user.mottagnings) {
        if (m.id === mottagning.id) {
          this.user.mottagnings.splice(i, 1);
          return;
        }
        i++;
      }
    } else {
      this.user.mottagnings.push(mottagning);
    }
  }


  onStatusClick(userStatus: boolean) {
    this.userStatus = !this.userStatus;
    if (this.userStatus) {
      this.user.status = 'Aktiv';
    } else {
      this.user.status = 'Inaktiv';
    }
  }

  userNameChange($event) {
    if (this.user.userName)
      this.http.get('/api/ad/' + this.user.userName).map(response => response.json()).subscribe(
        (us: Array<User>) => {
          if (us.length > 0) {
            this.user.passWord = '';
            this.demandPassword = false;
          } else {
            this.demandPassword = true;
          }
        }
      );

    if (this.inCreationMode)
      this.http.get('/api/user?userNameFilter=' + this.user.userName).map(response => response.json()).subscribe(
        (us: Array<User>) => {
          this.userNameClash = us.length > 0;
        }
      );
  }

}
