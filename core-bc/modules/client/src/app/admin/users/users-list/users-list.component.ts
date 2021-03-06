import {Component, OnInit, ViewChild} from '@angular/core';
import {Response} from '@angular/http';
import {Router} from '@angular/router';
import {ErrorHandler} from '../../../shared/error-handler';
import {User} from '../../../model/user';
import {JwtHttp} from '../../../core/jwt-http';
import {AuthService} from '../../../core/auth/auth.service';

import {MatDialog} from "@angular/material/dialog";
import {ConfirmDialogComponent} from "../../../shared/confirm-dialog/confirm-dialog.component";
import {MatPaginator, MatTableDataSource} from "@angular/material";
import {Subject} from "rxjs/Subject";


@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.scss']
})
export class UsersListComponent implements OnInit {

  users: User[];
  dataSources = new MatTableDataSource<User>();
  displayedColumns = ['anvandare', 'namn', 'behorighet','redigera'];
  @ViewChild('page1') page1 : MatPaginator;
  selected = 'Aktiv';
  selectedChanged= new Subject();
  //usersWithoutData: string[] = [];

  constructor(private http: JwtHttp,
              private errorHandler: ErrorHandler,
              private authService: AuthService,
              private router: Router,
              private dialog: MatDialog) { }

  ngOnInit() {
    this.updateUsers(this.selected);
    this.selectedChanged.subscribe(() => this.updateUsers(this.selected));
    /*this.http.get('/api/data/users').map<Response, string[]>(response => response.json())
    .subscribe(value => this.usersWithoutData = value);*/
  }
  doSearch(filterval: string){
    this.dataSources.filter = filterval;
  }
  private updateUsers(val: string) {
    this.http.get('/api/user').map<Response, User[]>(response => response.json())
      .subscribe(
        users => {
          this.dataSources.data = users.filter(user => user.status == val);
          this.dataSources.paginator = this.page1;
        }
      );
  }

/*
  confirmDeleteTODO(user: User) {
    this.http.delete('/api/user/' + user.id)
      .subscribe(response => {
        this.updateUsers();
      });
  }
*/
  confirmInactivateTODO(user: User) {
    user.inactivated = true;
    this.http.delete('/api/user/' + user.userName)
      .subscribe(response => {
        this.updateUsers(this.selected);
      });
  }

  canImpersonate() {
    return this.authService.canImpersonate();
  }

  impersonate(user: User) {
    this.http.post('/api/login/impersonate', user).subscribe(response => {
      this.authService.jwt = response.text();
      this.router.navigate(['/']);
    });
  }

  confirmDelete(user: User) {
    let dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        text: 'Är du säker att du vill ta bort vald användare?',
        confirmButtonText: 'Ta bort'
      },
      panelClass: 'dialys-dialog'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 'confirm') {
        this.http.delete('/api/user/' + user.userName)
          .subscribe(response => {
            this.updateUsers(this.selected);
          });
      }
    });
  }

  updateStatus()
  {
    //this.selected = this.selected == 'Aktiv'? 'Inaktiv': 'Aktiv';
    this.selectedChanged.next();
  }

}
