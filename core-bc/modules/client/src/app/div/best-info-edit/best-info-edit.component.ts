import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {Patient} from '../../model/Patient';
import {AuthService} from '../../core/auth/auth.service';
import {JwtHttp} from "../../core/jwt-http";
import {BestPDRad} from "../../model/BestPDRad";
import {BestInfo} from "../../model/BestInfo";
import {MatSnackBar} from "@angular/material";
import {Util} from "../../core/util/util";
import {Pd} from "../../model/Pd";

@Component({
  selector: 'best-info-edit',
  templateUrl: './best-info-edit.component.html',
  styleUrls: ['./best-info-edit.component.css']
})
export class BestInfoEditComponent implements OnInit {

  data: Patient = new Patient();
  bestInfo: BestInfo = new BestInfo();
  pd: Pd = new Pd();

  @Input()
  params: CustomParams;

  readonly: boolean = false;

  constructor(protected route: ActivatedRoute,
              protected http: JwtHttp,
              protected authService: AuthService,
              private snackBar: MatSnackBar,
              private router: Router) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
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
        var indexOfPd: number;
        var pdLoopIndex: number = 0;
        this.data.pds.forEach((pd) => {
          pd.bestInfos.forEach((bi) => {
            if (bi.id == pars.bestInfoId) {
              this.pd = pd;
              this.bestInfo = bi;
              indexOfPd = pdLoopIndex;
            }
          });
          pdLoopIndex++;
        });
        this.pd.sortBestInfos();
        this.readonly = this.pd.bestInfos.indexOf(this.bestInfo) != 0 || indexOfPd > 0;
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

    });
  }

  saveToServer() {
    if (!this.bestInfo.datum) {
      this.bestInfo.datum = new Date();
    }
    var clone: BestInfo = JSON.parse(JSON.stringify(this.bestInfo));
    clone.bestPDRads = clone.bestPDRads.filter(
      item => !(!item.antal || item.antal == 0)
    );

    if (clone.bestPDRads.length == 0) {
      this.snackBar.open('Beställ minst en vara innan du sparar.', null, {duration: 3000});
      return;
    }

    this.http.put('/api/bestInfo/', clone)
      .map(response => response.json())
      .share().subscribe((what: any) => {
      this.snackBar.open('Spara lyckades!', null, {duration: 3000});
      this.router.navigate(['/patienter', this.data.id]);
    });

  }

  print(title: string, printNodeId: string): boolean {
    let printContents = document.getElementById(printNodeId).innerHTML;
    printContents = "<style> .printing input, .printing textarea, .printing .zero-items { display: none; } </style> </style><div class='printing'>" + printContents + "</div>";
    return Util.print(title, printContents);
  }

  printThose(items: BestPDRad[]): BestPDRad[] {
    const result: BestPDRad[] = [];
    for (const item of items) {
      if (item.antal && item.antal > 0)
        result.push(item);
    }
    return result;
  }

  filter(these: Array<BestPDRad>): Array<BestPDRad> {
    const result: Array<BestPDRad> = [];
    if (this.readonly) {
      for (const that of these)
        if (that.antal > 0) result.push(that);
    } else {
      for (const that of these)
        if (that.antal > 0 || that.pdArtikel.artikel.aktiv)
          result.push(that);
    }
    return result;
  }

}

export interface CustomParams extends Params {
  pdId: number;
  patientId: number;
  bestInfoId: number;
}
