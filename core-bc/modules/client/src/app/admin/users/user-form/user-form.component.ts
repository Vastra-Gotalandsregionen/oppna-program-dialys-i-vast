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

  @Input('userId') userId;

  @Input('firstName') firstName;

  @Input('lastName') lastName;

  @Input('mail') mail;

  userForm: FormGroup;

  user: User;
  // selectedProdn1Ids: number[];

  constructor(private http: JwtHttp,
              private formBuilder: FormBuilder,
              private errorHandler: ErrorHandler,
              private router: Router) { }

  ngOnInit() {

    console.log('userId: ' + this.userId);

    if (this.userId) {
      console.log('found: ' + this.userId);
      const user$ = this.http.get('/api/user/' + this.userId)
        .map<Response, User>(response => response.json());

      Observable.forkJoin([user$])
        .subscribe((result: any[]) => {
          this.user = result[0];
          console.log(result);
          this.buildForm();
        });
    } else {
      console.log('did not find: ' + this.userId);
      this.user = new User();
      this.user.role = 'USER';
      this.buildForm();
    }
  }

  private buildForm() {

    this.userForm = this.formBuilder.group({
      'userId': [{value: this.user.id, disabled: false}, [Validators.required]],
      'firstName': [{value: this.user.firstName, disabled: false}, []],
      'lastName': [{value: this.user.lastName, disabled: false}, []],
      'mail': [{value: this.user.mail, disabled: false}, []],
      'roleGroup': this.formBuilder.group({
        'role': [{value: this.user.role, disabled: false}, [Validators.required]]
      })
    });

    let userIdField = this.userForm.get('userId');

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

    this.user.id = this.userForm.get('userId').value;
    this.user.firstName = this.userForm.get('firstName').value;
    this.user.lastName = this.userForm.get('lastName').value;
    this.user.mail = this.userForm.get('mail').value;

    this.user.role = this.userForm.get('roleGroup').get('role').value;
    this.user.inactivated = this.user.inactivated;

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
    const newUser = this.user.id !== value;

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
