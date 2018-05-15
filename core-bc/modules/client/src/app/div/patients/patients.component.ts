import {FormControl} from '@angular/forms';
import {Location} from '@angular/common';
import {Component, HostListener, OnInit} from '@angular/core';
import {RequestOptions, URLSearchParams} from '@angular/http';
import {Patient} from '../../model/Patient';
import {RestResponse} from '../../model/rest-response';
import {Observable} from 'rxjs/Observable';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from '../../core/auth/auth.service';
import {JwtHttp} from '../../core/jwt-http';
import {MatDialog, MatSnackBar} from "@angular/material";
import {ConfirmDialogComponent} from "../../shared/confirm-dialog/confirm-dialog.component";
import {StateService} from '../../core/state/state.service';
import {Pd} from "../../model/Pd";
import {BestInfo} from "../../model/BestInfo";

@Component({
  selector: 'app-dialys',
  templateUrl: './patients.component.html',
  styleUrls: ['./patients.component.css']
})
export class PatientsComponent implements OnInit {

  stateCtrl: FormControl;
  onlyActiveDatasCtrl: FormControl;
  status: string = 'Aktiv';

  returnurl:string;
  query: string;
  page = 0;
  selectedPage = 1;
  totalPagesArr: Array<number>;
  location: Location;
  pdRekviser: Pd[];
  hdRekviser: Pd[];
  patTyp: string;

  response: RestResponse<Patient>;
  sort: { field: string, ascending: boolean };

  constructor(private http: JwtHttp,
              location: Location,
              private route: ActivatedRoute,
              public authService: AuthService,
              private snackBar: MatSnackBar,
              private dialog: MatDialog,
              private stateService: StateService, private router: Router) {

    this.location = location;
    this.stateCtrl = new FormControl();
    this.onlyActiveDatasCtrl = new FormControl();
    // this.onlyMyDatasCtrl = new FormControl();
  }

  ngOnInit() {
    this.returnurl = this.router.url;
    this.route.queryParams
      .subscribe(params => {

        this.query = params.query;

        if (params.page) {
          this.page = Number(params.page);
          this.selectedPage = this.page + 1;
        }

        if (params.filter) {
          this.query = params.filter;
        }

        if (params.sort) {
          this.sort = {field: params.sort, ascending: params.asc === 'true'}
        }

        /*if (params.onlyActiveDatas) {
          this.onlyActiveDatas = params.onlyActiveDatas === 'true';
        } else {
          //this.onlyActiveDatas = this.loggedIn;
        }*/

        /*if (params.onlyMyDatas) {
          this.onlyMyDatas = params.onlyMyDatas === 'true';
        } else {
          //this.onlyMyDatas = this.loggedIn;
        }*/


        this.fetchDatas();

        /*this.stateCtrl.valueChanges
          .skip(1) // Skip on init
          .debounceTime(50) // Primarily to avoid many requests if user presses and holds backspace button.
          .subscribe(query => {
            this.query = query;
            this.updateState();
          });*/



        /*this.onlyActiveDatasCtrl.valueChanges
          .skip(1) // Skip on init
          .subscribe(value => {
            this.onlyActiveDatas = value;
            this.updateState();
          });*/

/*
        this.onlyMyDatasCtrl.valueChanges
          .skip(1) // Skip on init
          .subscribe(value => {
            this.onlyMyDatas = value;
            this.updateState();
          });
*/

      });
  }

  public filterTextChange(event) {
    if(event.keyCode == 13) {
      this.updateState();
    }
  }

  public updateState() {
    if (this.query || this.page > 0 || this.sort || /*this.onlyMyDatas ||*/ this.status) {
      const queryPart = (this.query ? '&query=' + this.query : '');
      const pagePart = (this.page > 0 ? '&page=' + this.page : '');
      const sortPart = (this.sort ? '&sort=' + this.sort.field + '&asc=' + this.sort.ascending : '');
      //const onlyMyDatasPart = (this.onlyMyDatas ? `&onlyMyDatas=${this.onlyMyDatas}` : '');
      // const onlyActiveDatasPart = (this.onlyActiveDatas ? `&onlyActiveDatas=${this.onlyActiveDatas}` : '');
      const statusPart = (this.status ? `&status=${this.status}` : '');

      let fullQueryPart = queryPart + pagePart + sortPart + /*onlyMyDatasPart +*/ statusPart;

      if (fullQueryPart.startsWith('&')) {
        fullQueryPart = fullQueryPart.substring(1);
      }

      this.location.replaceState('/patients', fullQueryPart);
    } else {
      this.location.replaceState('/patients');
    }

    this.selectedPage = this.page + 1;

    this.fetchDatas();
  }

  private fetchDatas() {
    this.observeData()
      .subscribe(response => {
        this.handleResponse(response);
      });
  }

  private handleResponse(response) {
    console.log('handleResponse', JSON.stringify(response));
    response.content.forEach((p: Patient) => {
      p.pds.sort((a: Pd, b: Pd) => (a.datum > b.datum ? -1 : 1));
      // Sort it backwards so that u will get the current pd first.
      if (p.pds.length > 0) {
        if (p.pds[0].bestInfos)
          p.pds[0].bestInfos.sort((a: BestInfo, b: BestInfo) => (a.datum > b.datum ? -1 : 1))
      }
    });
    console.log("Resultat", response.content);
    this.response = response;

    this.totalPagesArr = new Array(0);
    for (let i = 1; i <= this.response.totalPages; i++) {
      this.totalPagesArr.push(i);
    }

    if (this.page + 1 > this.response.totalPages) {
      // Reset pages and refetch.
      this.page = 0;

      if (this.response.totalPages > 0) {
        this.updateState();
      }
    }
  }

  private observeData(): Observable<any> {
    const params: URLSearchParams = new URLSearchParams();

    params.set('page', this.page + '');

    if (this.query) {
      params.set('query', this.query);
    }

    if (this.sort) {
      params.set('sort', this.sort.field);
      params.set('asc', this.sort.ascending + '');
    }

    if (this.status) {
      params.set('status', this.status + '');
    }

    /*if (!this.onlyMyDatas) {
      this.onlyMyDatas = false;
    }*/
    /*params.set('onlyMyDatas', this.onlyMyDatas + '');

    console.log(' this.onlyMyDatas: ' + this.onlyMyDatas);*/
    params.set("userName", this.authService.getLoggedInUserId());

    const requestOptions = new RequestOptions();
    requestOptions.params = params;
    // console.log('reguestOptions: ', requestOptions);
    return this.http.get('/api/patient/filter', requestOptions).map(response => response.json());
  }

  @HostListener('window:keydown', ['$event'])
  paginateByArrowKey($event) {
    if ($event.key) {
      if ($event.key.toLowerCase().indexOf('right') > -1) {
        this.nextPage();
      } else if ($event.key.toLowerCase().indexOf('left') > -1) {
        this.previousPage()
      }
    }
  }


  nextPage(): void {
    if (this.page + 1 < this.response.totalPages) {
      this.page++;
      this.updateState();
    }
  }

  previousPage(): void {
    if (this.page > 0) {
      this.page--;
      this.updateState();
    }
  }

  goToSelectedPage(): void {
    this.page = this.selectedPage - 1;
    this.updateState();
  }

  toggleSort(field: string) {
    if (this.sort && this.sort.field === field) {
      const currentAscending = this.sort.ascending;
      this.sort = {field: field, ascending: !currentAscending};
    } else {
      this.sort = {field: field, ascending: true}
    }

    this.updateState();
  }

  userHasEditPermission(data: Patient) {
    return this.authService.userHasDataEditPermission(data);
  }

  patientHarRekvisition(patient: Patient):boolean
  {
    Patient.init(patient);
    patient.sortPds();
    this.patTyp = patient.typ;
    if (this.patTyp == 'PD')
    {
      this.pdRekviser = patient.pds.filter(item => item.typ == 'PD')
      return this.pdRekviser.length != 0 ? true : false;
    }
    else if(this.patTyp == 'HD')
    {
      this.hdRekviser = patient.pds.filter(item => item.typ == 'HD')
      return this.hdRekviser.length != 0 ? true : false;
    }
  }

  get loggedIn() {
    return this.authService.jwt ? true : false;
  }

  get admin() {
    return this.authService.isAdmin() || this.authService.getAdmin();
  }

/*
  confirmDelete(data: Patient) {
    let dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        text: 'Är du säker att du vill ta bort vald arbetsplatskod?',
        confirmButtonText: 'Ta bort'
      },
      panelClass: 'dialys-dialog'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 'confirm') {
        this.http.delete('/api/data/' + data.id)
          .subscribe(response => {
            // console.log(response);
            this.updateState();
            this.snackBar.open('Lyckades spara!', null, {duration: 3000});
          });
      }
    });
  }
*/

}
