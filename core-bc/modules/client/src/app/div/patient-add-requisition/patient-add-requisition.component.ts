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

@Component({
  selector: 'app-apk-detail',
  templateUrl: './patient-add-requisition.component.html',
  styleUrls: ['./patient-add-requisition.component.css']
})
export class PatientAddRequisitionComponent implements OnInit {

  @ViewChild(ApkFormComponent) apkFormComponent: ApkFormComponent;

  id: string;
  data: Patient;
  displayedColumns = ['namn', 'storlek', 'artNr', 'ordination', 'maxantal'];

  @Input()
  selectedArtiklar = [];

  pd: Pd = new Pd();

  private fliks: Array<Flik>;

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
          this.pd.patient = data;
          this.pd.patient.pds.sort((a: Pd, b: Pd) => (a.datum > b.datum ? -1 : 1));
          // So that the latest and current pd will be at position 0 in the list. 'datum' might be changed to 'giltig'?
          this.pd.datum = new Date();

          const $fliks = this.http.get('/api/flik')
            .map(response => response.json())
            .share();
          $fliks.subscribe((data: Array<Flik>) => {
            this.fliks = data;
            /*this.fliks.sort((a: Flik, b: Flik) => {

            })*/
            this.fliks.forEach((flik: Flik) => {
              flik.grupps.forEach((grupp: Grupp) => {
                grupp.artikels.sort((a: Artikel, b: Artikel) => (a.namn > b.namn ? 1 : -1));
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
      let pdArtikel: PDArtikel = new PDArtikel();
      pdArtikel.artikel = rad;
      this.pd.pdArtikels.push(pdArtikel);
    }
  }

  saveToServer() {
    const $data = this.http.put('/api/pd/', this.pd)
      .map(response => response.json())
      .share();
    $data.subscribe((data: Pd) => {
      this.pd = data;
    });
  }

}
