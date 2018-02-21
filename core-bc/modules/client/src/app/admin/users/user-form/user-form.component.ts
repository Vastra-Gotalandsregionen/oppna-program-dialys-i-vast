import {Component, Input, OnInit} from '@angular/core';
import {ErrorHandler} from '../../../shared/error-handler';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Response} from '@angular/http';
import {User} from '../../../model/user';
import {Observable} from 'rxjs/Observable';
import {JwtHttp} from '../../../core/jwt-http';
import {Router} from '@angular/router';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.scss']
})
export class UserFormComponent implements OnInit {

  saveMessage: string;

  @Input('userName') userName;

  @Input('name') name;

  /*@Input('passWord') passWord;

  @Input('typ') typ;*/

  /*@Input('lastName') lastName;
  @Input('mail') mail;*/

  userForm: FormGroup;

  user: User;
  // selectedProdn1Ids: number[];

  constructor(private http: JwtHttp,
              private formBuilder: FormBuilder,
              private errorHandler: ErrorHandler,
              private router: Router) { }

  ngOnInit() {

    console.log('userName: ' + this.userName);

    if (this.userName) {
      console.log('found: ' + this.userName);
      const user$ = this.http.get('/api/user/' + this.userName)
        .map<Response, User>(response => response.json());

      Observable.forkJoin([user$])
        .subscribe((result: any[]) => {
          this.user = result[0];
          console.log(result);
          this.buildForm();
        });
    } else {
      console.log('did not find: ' + this.userName);
      this.user = new User();
      //this.user.role = 'USER';
      this.user.typ = 'PD';
      this.buildForm();
    }
  }

  private buildForm() {

    this.userForm = this.formBuilder.group({
      'userName': [{value: this.user.userName, disabled: false}, [Validators.required]],
      'name': [{value: this.user.name, disabled: false}, []],
      'passWord': [{value: this.user.passWord, disabled: false}, [Validators.required]],
      'typ': [{value: this.user.typ, disabled: false}, []],
      /*'lastName': [{value: this.user.lastName, disabled: false}, []],
      'mail': [{value: this.user.mail, disabled: false}, []],*/
      /*'roleGroup': this.formBuilder.group({
        'role': [{value: this.user.role, disabled: false}, [Validators.required]]
      }),*/
      'typGroup': this.formBuilder.group({
        'typ': [{value: this.user.typ, disabled: false}, [Validators.required]]
      }),
      'usersRole': this.formBuilder.group({
        'typ': [{value: this.user.typ, disabled: false}, [Validators.required]]
      })
    });

    let userIdField = this.userForm.get('userName');

    userIdField.valueChanges
      .subscribe(value => {
        if (value) {
          let trimmed = value.trim();
          if (trimmed !== value) {
            userIdField.setValue(trimmed);
          }
        }
      });

    userIdField.setAsyncValidators(this.validateUserId.bind(this))
  }

  save() {
    if (!this.userForm.valid) {
      this.saveMessage = 'Alla fält är inte korrekt ifyllda.';
      return;
    }

    this.user.userName = this.userForm.get('userName').value;
    this.user.name = this.userForm.get('name').value;
    this.user.passWord = this.userForm.get('passWord').value;
    console.log("passWord", this.user.passWord);

    this.user.typ = this.userForm.get('typGroup').get('typ').value;

    /*this.user.role = this.userForm.get('roleGroup').get('role').value;
    this.user.inactivated = this.user.inactivated;*/

    this.http.put('/api/user', this.user)
      .subscribe(result => {
        this.router.navigate(['/admin/users']);
      });
  }

  // Convenience method for less code in html file.
  getErrors(formControlName: string): any {
    return this.userForm.controls[formControlName].errors;
  }

  validateUserId(control: AbstractControl): Observable<{ [key: string]: any }> {
    let value = control.value;
    const newUser = this.user.userName !== value;

    if (value && newUser) {
      return this.http.get('/api/user/' + value)
        .map(response => {
          try {
            return response.json();
          } catch (e) {
            return null;
          }
        })
        .map(user => {
          if (user) {
            control.markAsTouched();
            return {alreadyExists: control.value};
          } else {
            return null;
          }
        });
    } else {
      return new Observable(observer => observer.next(null)).take(1);
    }
  };

}
