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

  userNames: Array<String> = [];

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
    console.log('userName: ' + this.userName);
    console.log('this.user.userName: ' + this.user.userName);
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
      console.log('This is a new user!');
      this.user = new User();
    }

    this.http.get('/api/mottagning').map(response => response.json()).subscribe(
      (ms: Array<Mottagning>) => {
        this.mottagnings = ms;
      }
    );

    this.http.get('/api/user/').map(response => response.json()).subscribe(
      (us: Array<User>) => {
        console.log('u', us);
        us.forEach(u => this.userNames.push(u.userName));
      }
    );


  }

  save() {
    var error: string;
    if (!this.user.userName || this.user.userName.trim() == '') {
      error += '\nAnvändarnamn för inte vara tomt.';
    }

    if (this.inCreationMode) {

    } else {

    }

    this.http.put('/api/user/', this.user)
      .subscribe(result => {
        this.router.navigate(['/admin/users']);
      });

    console.log(error);
  }


  checkPasswordNeed() {
    console.log('checkPasswordNeed');
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
    console.log('onStatusClick', this.user);
  }

  userNameChange($event) {
    console.log("$event", $event);
    if (this.user.userName)
      this.http.get('/api/ad/' + this.user.userName).map(response => response.json()).subscribe(
        (us: Array<User>) => {
          console.log("us", us);
          if (us.length > 0) {
            this.user.passWord = '';
            this.demandPassword = false;
            console.log("Us 1", us);
          } else {
            this.demandPassword = true;
            console.log("Us 2", us);
          }
        }
      );

    if (this.inCreationMode)
      this.http.get('/api/user?userNameFilter=' + this.user.userName).map(response => response.json()).subscribe(
        (us: Array<User>) => {
          console.log('u', us);
          this.userNameClash = us.length > 0;
        }
      );
  }

}
