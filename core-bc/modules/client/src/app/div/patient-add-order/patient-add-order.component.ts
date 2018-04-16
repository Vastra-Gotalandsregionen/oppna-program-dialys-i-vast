import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Patient} from '../../model/Patient';
import {AuthService} from '../../core/auth/auth.service';
import {Observable} from 'rxjs/Observable';
import {JwtHttp} from "../../core/jwt-http";
import {Artikel} from "../../model/Artikel";
import {PDArtikel} from "../../model/PDArtikel";
import {BestPDRad} from "../../model/BestPDRad";
import {BestInfo} from "../../model/BestInfo";
import {Pd} from "../../model/Pd";
import {MatSnackBar} from "@angular/material";
import {identifierModuleUrl} from "@angular/compiler";
import {FormsModule, NgForm} from "@angular/forms";

@Component({
  selector: 'app-apk-detail',
  templateUrl: './patient-add-order.component.html',
  styleUrls: ['./patient-add-order.component.css']
})
export class PatientAddOrderComponent implements OnInit {

  id: string;
  data: Patient;
  artikels: PDArtikelRow[];
  rekvisId: number;
  rekvisdatum: Date;
  displayedColumns = ['artikel', 'mangd', 'pdartikel','maxantal', 'antal'];

  bestInfo: BestInfo = new BestInfo();


  constructor(protected route: ActivatedRoute,
              protected http: JwtHttp,
              protected authService: AuthService,
              private snackBar: MatSnackBar,
              private router: Router) {
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

          $articles.subscribe((dat: PDArtikelRow[]) => {
            this.artikels = dat;
            for (let pdArtikelRow of dat) {
              var bestPdRow = new BestPDRad();
              bestPdRow.pdartikelID = pdArtikelRow.id;
              pdArtikelRow.bestPdRow = bestPdRow;
              this.bestInfo.bestPDRads.push(bestPdRow);

            }
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

  saveToServer(orderModel: NgForm) {

    for (let rad of this.bestInfo.bestPDRads) {
      this.bestInfo.bestPDRads = this.bestInfo.bestPDRads.filter(item => item.antal != null
      || item.antal > 0);

      /*if (rad.antal == null || rad.antal < 1) {
        console.log("rad" + this.bestInfo.bestPDRads.indexOf(rad));
        this.bestInfo.bestPDRads.splice(this.bestInfo.bestPDRads.indexOf(rad)-1, 1);
       /* this.bestInfo.bestPDRads.forEach( (item, index) => {
          if(item.antal == null || item.antal < 1) this.bestInfo.bestPDRads.splice(index,1);
        });*/
      //}*/
    }
    this.bestInfo.datum = new Date();
    this.bestInfo.pdid = +this.rekvisId;
    const $data = this.http.put('/api/bestInfo/', this.bestInfo)
      .map(response => response.json())
      .share();
    $data.subscribe((patient: BestInfo) => {
      console.log("saveToServer callback");
      this.snackBar.open('Lyckades spara!', null, {duration: 3000});
      console.log("saveToServer callback - end");
      console.log(orderModel.form);
      console.log('saved' + JSON.stringify(orderModel.value))
      this.router.navigate(['../../'], {relativeTo: this.route})
    });


  }

}

export class PDArtikelRow extends PDArtikel {
  bestPdRow: BestPDRad;
}
