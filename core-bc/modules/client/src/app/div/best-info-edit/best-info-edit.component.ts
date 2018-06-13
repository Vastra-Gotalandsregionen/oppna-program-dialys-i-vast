import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {Patient} from '../../model/Patient';
import {AuthService} from '../../core/auth/auth.service';
import {JwtHttp} from "../../core/jwt-http";
import {PDArtikel} from "../../model/PDArtikel";
import {BestPDRad} from "../../model/BestPDRad";
import {BestInfo} from "../../model/BestInfo";
import {MatSnackBar} from "@angular/material";
import {NgForm} from "@angular/forms";
import {Util} from "../../core/util/util";
import {Pd} from "../../model/Pd";

@Component({
  selector: 'best-info-edit',
  templateUrl: './best-info-edit.component.html',
  styleUrls: ['./best-info-edit.component.css']
})
export class BestInfoEditComponent implements OnInit {

  /*  id: string;

    artikels: PDArtikelRow[];
    rekvisId: number;
    rekvisdatum: Date;*/

  data: Patient = new Patient();
  bestInfo: BestInfo = new BestInfo();
  pd: Pd = new Pd();

  @Input()
  params: CustomParams;

  constructor(protected route: ActivatedRoute,
              protected http: JwtHttp,
              protected authService: AuthService,
              private snackBar: MatSnackBar,
              private router: Router) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      console.log("params", params);
      this.params = <CustomParams> params;

      this.initData(this.params);
    });
  }

  protected initData(pars: CustomParams) {
    this.http.get('/api/patient/' + pars.patientId)
      .map(response => response.json()).subscribe((data: Patient) => {
      this.data = data;
      Patient.init(this.data);
      this.data.sortPds();

      if (pars.bestInfoId) {
        // Edit an existing BestInfo.
        this.data.pds.forEach((pd) => {
          pd.bestInfos.forEach((bi) => {
            if (bi.id == pars.bestInfoId){
              this.pd = pd;
              this.bestInfo = bi;
            }
          });
        });
        console.log('pars.bestInfoId', pars.bestInfoId);

      } else {
        // Create a new BestInfo
        this.pd = this.data.pds[0];
        this.bestInfo = new BestInfo();
        this.bestInfo.pd = this.pd;
        this.bestInfo.pdid = this.pd.id;
      }

      // For both new and present items - fill bestInfo with rows for those artikles not yet there. All for new ones.
      const pdArtikelIdsInsideBestInfo: Array<number> = [];
      this.bestInfo.bestPDRads.forEach((bpr) => {
        pdArtikelIdsInsideBestInfo.push(bpr.pdArtikel.id);
      });

      this.pd.pdArtikels.forEach((artikel) => {
        if (pdArtikelIdsInsideBestInfo.indexOf(artikel.id) == -1) {
          var newBestPdRad = new BestPDRad();
          newBestPdRad.pdArtikel = artikel;
          newBestPdRad.pdartikelID = artikel.id;
          this.bestInfo.bestPDRads.push(newBestPdRad);
        }
      });

      this.bestInfo.bestPDRads.sort(
        (b1, b2) => b1.pdArtikel.artikel.namn > b2.pdArtikel.artikel.namn ? +1 : -1
      );

      console.log("pd", this.pd);
      console.log("bestInfo", this.bestInfo);
      console.log("data", this.data);
    });
  }

  userHasEditPermission(data: Patient) {
    return this.authService.userHasDataEditPermission(data);
  }

  saveToServer(orderModel: NgForm) {
    if (!this.bestInfo.datum) {
      this.bestInfo.datum = new Date();
    }
    var clone: BestInfo = JSON.parse(JSON.stringify(this.bestInfo));
    clone.bestPDRads = clone.bestPDRads.filter(
      item => item.antal != null || item.antal > 0
    );

    if (clone.bestPDRads.length == 0) {
      this.snackBar.open('Beställ minst en vara innan du sparar.', null, {duration: 3000});
      return;
    }

    console.log('before dave', clone);

    this.http.put('/api/bestInfo/', clone)
      .map(response => response.json())
      .share().subscribe((what: any) => {
      this.snackBar.open('Spara lyckades!', null, {duration: 3000});
      this.router.navigate(['/patienter', this.data.id]);
    });

    /*
        for (let rad of this.bestInfo.bestPDRads) {
          this.bestInfo.bestPDRads = this.bestInfo.bestPDRads.filter(item => item.antal != null
            || item.antal > 0);
        }
        this.bestInfo.datum = new Date();
        this.bestInfo.fritext = orderModel.form.controls.fritxt.value;
        this.bestInfo.levDatum = orderModel.form.controls.leveransdatum.value;
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
          this.router.navigate(['/patienter', this.id])
        });*/


  }

  print(title: string, printNodeId: string): boolean {
    let printContents = document.getElementById(printNodeId).innerHTML;
    return Util.print(title, printContents);
  }

  logProps(pdArtikel: any) {
    for (var k in pdArtikel)
      console.log(k, pdArtikel[k]);
    return '';
  }
}

export class PDArtikelRow extends PDArtikel {
  bestPdRow: BestPDRad;
}

export interface CustomParams extends Params {
  pdId: number;
  patientId: number;
  bestInfoId: number;
}
