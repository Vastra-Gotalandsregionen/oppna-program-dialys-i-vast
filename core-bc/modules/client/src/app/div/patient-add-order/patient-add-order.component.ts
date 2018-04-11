import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Patient} from '../../model/Patient';
import {AuthService} from '../../core/auth/auth.service';
import {Observable} from 'rxjs/Observable';
import {ApkFormComponent} from "../apk-form/apk-form.component";
import {JwtHttp} from "../../core/jwt-http";
import {Artikel} from "../../model/Artikel";
import {PDArtikel} from "../../model/PDArtikel";

@Component({
  selector: 'app-apk-detail',
  templateUrl: './patient-add-order.component.html',
  styleUrls: ['./patient-add-order.component.css']
})
export class PatientAddOrderComponent implements OnInit {

  @ViewChild(ApkFormComponent) apkFormComponent: ApkFormComponent;

  id: string;
  data: Patient;
  artikels: PDArtikel[];
  rekvisId: number;
  rekvisdatum: Date;
  displayedColumns = ['artikel', 'mangd', 'pdartikel','maxantal', 'antal'];

  constructor(protected route: ActivatedRoute,
              protected http: JwtHttp,
              protected authService: AuthService) {
  }

  ngOnInit() {


    this.route.params.subscribe(params => {
      this.id = params.id;

      if (this.id) {
        const $data = this.http.get('/api/patient/' + this.id)
          .map(response => response.json())
          .share();

        $data.subscribe((data: Patient) => {
          this.data = data;
          this.rekvisId = data.pds[0].id;
          this.rekvisdatum = data.pds[0].datum;
          const $articles = this.http.get('/api/artikels/' + this.rekvisId)
            .map(response => response.json())
            .share();

          $articles.subscribe((dat: PDArtikel[]) => {
            this.artikels = dat;

          });
        });
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
