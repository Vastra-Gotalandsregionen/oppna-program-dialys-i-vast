import {Component, Input, NgModule, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Patient} from '../../model/Patient';
import {AuthService} from '../../core/auth/auth.service';
import {JwtHttp} from "../../core/jwt-http";
import {Flik} from "../../model/Flik";
import {Pd} from "../../model/Pd";
import {PDArtikel} from "../../model/PDArtikel";
import {Grupp} from "../../model/Grupp";
import {Artikel} from "../../model/Artikel";
import {MatSnackBar} from "@angular/material";
import {RequisitionEditComponent} from "../requisition-edit/requisition-edit.component";
import {until} from "selenium-webdriver";
import elementIsSelected = until.elementIsSelected;

@Component({
  selector: 'app-apk-detail',
  templateUrl: './patient-add-requisition.component.html',
  styleUrls: ['./patient-add-requisition.component.css'],
})
export class PatientAddRequisitionComponent implements OnInit {

  @ViewChild(RequisitionEditComponent) requisitionEditComponent: RequisitionEditComponent;

  id: string;
  patient: Patient;
  latestPd: Pd;
  returnurl: string;
  displayedColumns = ['namn', 'storlek', 'artNr', 'ordination', 'maxantal'];

  @Input()
  selectedArtiklar = [];

  @Input()
  saving: boolean = false;

  artikelToPdArtikels: Map<Artikel, PDArtikel> = new Map();

  pd: Pd = new Pd();

  public fliks: Array<Flik>;

  constructor(protected route: ActivatedRoute,
              protected http: JwtHttp,
              protected authService: AuthService,
              private snackBar: MatSnackBar, private router:Router) {
  }

  ngOnInit() {
    this.returnurl = this.route.snapshot.queryParams['returnUrl'];
    this.route.params.subscribe(params => {
      this.id = params.id;

      if (this.id) {
        const $patientRpc = this.http.get('/api/patient/' + this.id)
          .map(response => response.json())
          .share();
        $patientRpc.subscribe((patient: Patient) => {
          this.patient = patient;
          this.pd.patient = patient;

          Patient.init(this.pd.patient);
          this.pd.patient.sortPds();

          var pdArtikelsByArtikelKey: Map<number, PDArtikel> = new Map();
          if (this.pd.patient.pds.length > 0) {
            this.latestPd = this.patient.pds[0];
          } else {
            this.latestPd = new Pd();
          }
          this.latestPd.pdArtikels.forEach((pdArtikel: PDArtikel) => {
            pdArtikelsByArtikelKey.set(pdArtikel.artikel.id, pdArtikel);
          });

          // So that the latest and current pd will be at position 0 in the list. 'datum' might be changed to 'giltig'?
          this.pd.datum = new Date();

          const $fliks = this.http.get('/api/flik?typ=' + this.patient.typ)
            .map(response => response.json())
            .share();
          $fliks.subscribe((patient: Array<Flik>) => {
            this.fliks = patient;
            this.fliks.forEach((flik: Flik) => {
              flik.grupps.forEach((grupp: Grupp) => {
                grupp.artikels.sort((a: Artikel, b: Artikel) => (a.namn > b.namn ? 1 : -1));
                grupp.artikels.forEach((artikel: Artikel) => {
                  var pdArtikel = new PDArtikel();
                  pdArtikel.artikel = artikel;
                  this.artikelToPdArtikels.set(artikel, pdArtikel);
                  if (pdArtikelsByArtikelKey.has(artikel.id)) {
                    pdArtikel.artikel = artikel;
                    this.selectedArtiklar.push(artikel);
                    this.pd.pdArtikels.push(pdArtikel);
                    pdArtikel.maxantal = pdArtikelsByArtikelKey.get(artikel.id).maxantal;
                  }
                });
              });

            });
            this.putArtikelCountIntoTreeNodes();
          });

        });
      }
    });
  }

  putArtikelCountIntoTreeNodes() {
    this.asFlikReqs(this.fliks).forEach(
      flik=> {
        var sum = 0;
        this.asGruppReqs(flik.grupps).forEach(
          grupp => {
            var gruppSum = 0;
            grupp.artikels.forEach(artikel => {
              if (this.selectedArtiklar.indexOf(artikel) > -1) gruppSum++;
            });
            grupp.artikelCount = gruppSum;
            sum += gruppSum;
          }
        );
        flik.artikelCount = sum;
      }
    );
  }

  protected getId() {
    return this.id;
  }

  userHasEditPermission(patient: Patient) {
    return this.authService.userHasDataEditPermission(patient);
  }

  onItemSelect(rad) {
    var index: number = this.selectedArtiklar.indexOf(rad);
    if (index != -1) {
      this.pd.pdArtikels.splice(index, 1);
      this.selectedArtiklar.splice(index, 1);
    } else {
      this.selectedArtiklar.push(rad);
      // let pdArtikel: PDArtikel = new PDArtikel();
      let pdArtikel: PDArtikel = this.artikelToPdArtikels.get(rad);
      pdArtikel.artikel = rad;
      this.pd.pdArtikels.push(pdArtikel);
    }
    this.putArtikelCountIntoTreeNodes();
  }

  saveToServer() {
    console.log("saveToServer start");
    this.saving = true;
    const $data = this.http.put('/api/pd/', this.pd)
      .map(response => response.json())
      .share();
    $data.subscribe((patient: Pd) => {
      console.log("saveToServer callback");
      //this.pd = patient;
      this.snackBar.open('Lyckades spara!', null, {duration: 3000});
      this.saving = false;
      console.log("saveToServer callback - end");
    });
    console.log("saveToServer end");
  }

  avbryt(){
    if (this.returnurl)
    {
      this.router.navigateByUrl(this.returnurl);
    }
    else
    {
      this.router.navigate(['/patienter', this.id])
    }
  }

  asGruppReqs(grupps: Array<Grupp>): GruppReq[] {
    return <GruppReq[]> grupps;
  }

  asFlikReqs(fliks: Array<Flik>): FlikReq[] {
    return <FlikReq[]> fliks;
  }
}


export class FlikReq extends Flik {
  artikelCount: number;

  public static asFlikReq(flik: Flik): FlikReq {
    return <FlikReq> flik;
  }

  public static asFlikReqs(fliks: Flik[]): FlikReq[] {
    return <FlikReq[]> fliks;
  }

}

export class GruppReq extends Grupp {
  artikelCount: number;
}



/*
class ArtikelReq extends Artikel {
  editable: boolean = false;
  pdArtikelsCount: number;
}*/
