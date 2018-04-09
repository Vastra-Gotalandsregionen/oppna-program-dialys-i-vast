import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Patient} from '../../model/Patient';
import {AuthService} from '../../core/auth/auth.service';
import {ApkFormComponent} from "../apk-form/apk-form.component";
import {JwtHttp} from "../../core/jwt-http";
import {Flik} from "../../model/Flik";
import {Pd} from "../../model/Pd";
import {PDArtikel} from "../../model/PDArtikel";
import {Grupp} from "../../model/Grupp";
import {Artikel} from "../../model/Artikel";
import {MatSnackBar} from "@angular/material";

@Component({
  selector: 'app-apk-detail',
  templateUrl: './patient-add-requisition.component.html',
  styleUrls: ['./patient-add-requisition.component.css']
})
export class PatientAddRequisitionComponent implements OnInit {

  @ViewChild(ApkFormComponent) apkFormComponent: ApkFormComponent;

  id: string;
  data: Patient;
  latestPd: Pd;
  displayedColumns = ['namn', 'storlek', 'artNr', 'ordination', 'maxantal'];

  @Input()
  selectedArtiklar = [];

  @Input()
  saving: boolean = false;

  artikelToPdArtikels: Map<Artikel, PDArtikel> = new Map();

  pd: Pd = new Pd();

  private fliks: Array<Flik>;

  constructor(protected route: ActivatedRoute,
              protected http: JwtHttp,
              protected authService: AuthService,
              private snackBar: MatSnackBar) {
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
          this.pd.patient = data;

          this.pd.patient.pds.sort((a: Pd, b: Pd) => (a.datum > b.datum ? -1 : 1));
          var pdArtikelsByArtikelKey: Map<number, PDArtikel> = new Map();
          if (this.pd.patient.pds.length > 0) {
            this.latestPd = this.data.pds[0];
          } else {
            this.latestPd = new Pd();
          }
          this.latestPd.pdArtikels.forEach((pdArtikel: PDArtikel) => {
            pdArtikelsByArtikelKey.set(pdArtikel.artikel.id, pdArtikel);
          });

          console.log("latestPd", this.latestPd);

          // So that the latest and current pd will be at position 0 in the list. 'datum' might be changed to 'giltig'?
          this.pd.datum = new Date();

          const $fliks = this.http.get('/api/flik?typ=' + this.data.typ)
            .map(response => response.json())
            .share();
          $fliks.subscribe((data: Array<Flik>) => {
            this.fliks = data;
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

  onItemSelect(rad) {
    var index: number = this.selectedArtiklar.indexOf(rad);
    if (index != -1) {
      this.pd.pdArtikels.splice(index);
      this.selectedArtiklar.splice(index);
    } else {
      this.selectedArtiklar.push(rad);
      // let pdArtikel: PDArtikel = new PDArtikel();
      let pdArtikel: PDArtikel = this.artikelToPdArtikels.get(rad);
      pdArtikel.artikel = rad;
      this.pd.pdArtikels.push(pdArtikel);
    }
  }

  saveToServer() {
    console.log("saveToServer start");
    this.saving = true;
    const $data = this.http.put('/api/pd/', this.pd)
      .map(response => response.json())
      .share();
    $data.subscribe((data: Pd) => {
      console.log("saveToServer callback");
      //this.pd = data;
      this.snackBar.open('Lyckades spara!', null, {duration: 3000});
      this.saving = false;
      console.log("saveToServer callback - end");
    });
    console.log("saveToServer end");
  }

}
