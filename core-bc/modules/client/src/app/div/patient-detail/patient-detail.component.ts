import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Patient} from '../../model/Patient';
import {AuthService} from '../../core/auth/auth.service';
import {Observable} from 'rxjs/Observable';
import {ApkFormComponent} from "../apk-form/apk-form.component";
import {JwtHttp} from "../../core/jwt-http";
import {Rekvistion} from "./Rekvistion";
import {BestInfo} from "../../model/BestInfo";
import {DataSource} from '@angular/cdk/collections'



@Component({
  selector: 'app-apk-detail',
  templateUrl: './patient-detail.component.html',
  styleUrls: ['./patient-detail.component.css']
})
export class PatientDetailComponent implements OnInit {

  @ViewChild(ApkFormComponent) apkFormComponent: ApkFormComponent;

  id: string;
  data: Patient;
  showOldRequisitions: boolean;
  $replacedBy: Observable<Patient>;
  $replaces: Observable<Patient>;
  archivedDatas: Patient[] = [];
  rekvisition: Rekvistion = new Rekvistion();
  dataSource: BestInfo[] = [];
  displayedColumns = ['id', 'datum', 'utskrivare'];
  panelOpenState: Number[] = [];

  constructor(protected route: ActivatedRoute,
              protected http: JwtHttp,
              protected authService: AuthService) {
  }

  ngOnInit() {


    this.route.params.subscribe(params => {
      this.id = params.id;

      this.showOldRequisitions = false;

      if (this.id) {
        const $data = this.http.get('/api/patient/' + this.id)
          .map(response => response.json())
          .share();

        $data.subscribe((data: Patient) => {
          this.data = data;
          this.rekvisition.datum = new Date(data.pds[0].datum);
          this.rekvisition.id = +data.pds[0].id;
          this.dataSource = data.pds[0].bestInfos;

        });

/*        this.$replacedBy = $data
          .filter((data: Patient) => !!data.ersattav)
          .mergeMap((data: Patient) => this.http.get('/api/data/arbetsplatskodlan/' + data.ersattav))
          .map(response => response.json());

        this.$replaces = $data
          .mergeMap((data: Patient) => this.http.get('/api/data/ersattav/' + data.arbetsplatskodlan))
          .map(response => response.json());*/

/*
        this.http.getPage('/api/archivedData/' + this.id)
          .map(response => response.json())
          .subscribe((archivedDatas: Patient[]) => {
            this.archivedDatas = archivedDatas;
          });
*/

      }
    });
  }

  protected getId() {
    return this.id;
  }

  userHasEditPermission(data: Patient) {
    return this.authService.userHasDataEditPermission(data);
  }
}
