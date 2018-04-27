import {Component, OnInit} from '@angular/core';
import {JwtHttp} from "../../../core/jwt-http";
import {Flik} from "../../../model/Flik";
import {Grupp} from "../../../model/Grupp";
import {Artikel} from "../../../model/Artikel";
import {FlikRot} from "../../../model/FlikRot";

@Component({
  selector: 'app-artikels-list',
  templateUrl: './artikels-list.component.html',
  styleUrls: ['./artikels-list.component.scss']
})
export class ArtikelsListComponent implements OnInit {

  constructor(private http: JwtHttp) {
  }

  fliks: Array<Flik>;

  flikRot: FlikRot;

  public columns = ['name'];

  ngOnInit() {
    this.fetchDataFromServer();
  }

  fetchDataFromServer() {
    this.http.get('/api/flikrot/default').map(response => response.json()).subscribe(
      (flikRot: FlikRot) => {
        this.flikRot = flikRot;
        this.fliks = flikRot.fliks;
        console.log('fliks', this.fliks);
        const groups:Grupp[] = [];
        this.fliks.forEach(f => f.grupps.forEach(g => groups.push(g)));
        this.fetchArtikelUsageFromServer(groups);
        this.startFetchGruppsArtikelUsageFromServer();
      }
    );
  }

  startFetchGruppsArtikelUsageFromServer() {
    const groups:Grupp[] = [];
    this.fliks.forEach(f => f.grupps.forEach(g => groups.push(g)));
    this.fetchGruppsArtikelUsageFromServer(groups);
  }

  fetchGruppsArtikelUsageFromServer(groups: Grupp[]) {
    var ids: number[] = [];
    groups.forEach(i => ids.push(i.id));
    var url = '/api/generic/counts/Grupp/artikels.pdArtikels/iids/' + ids;
    this.http.get(url).map(response => response.json()).subscribe(
      (counts: number[]) => {
        for (var i = 0; i < counts.length; i++) {
          this.toGroupExt(groups[i]).pdArtikelsCount = counts[i];
          this.toGroupExt(groups[i]).removeable = (counts[i] === 0);
        }
        this.fetchArtikelUsageFromServer(groups);
      });

  }

  fetchArtikelUsageFromServer(groups: Grupp[]) {
    if (groups.length === 0) return;
    var ids: number[] = [];
    const last = groups[groups.length - 1];
    groups.splice(groups.length - 1);
    last.artikels.forEach((i) => ids.push(i.id));
    if (ids.length === 0) {
      this.fetchArtikelUsageFromServer(groups);
      return;
    }
    var url = '/api/generic/counts/Artikel/pdArtikels/iids/' + ids;
    this.http.get(url).map(response => response.json()).subscribe(
      (counts: number[]) => {
        for (var i = 0; i < counts.length; i++) {
          this.toArtikelExt(last.artikels[i]).pdArtikelsCount = counts[i];
          this.toArtikelExt(last.artikels[i]).editable = (counts[i] === 0);
        }
        this.fetchArtikelUsageFromServer(groups);
      });
  }

  toArtikelExt(artikel: Artikel): ArtikelExt {
    return <ArtikelExt> artikel;
  }

  createNewFlik() {
    this.fliks.unshift(new Flik());
  }

  saveToServerSide() {
    this.http.put('/api/flikrot', this.flikRot).map(response => response.json()).subscribe(
      (fliks: Array<Flik>) => {
        console.log('saved-fliks', fliks);
        this.fetchDataFromServer();
      }
    );
  }

  toArtikelExts(artikels: Array<Artikel>): ArtikelExt[] {
    /*artikels.forEach(artikel => {
      artikel['prototype'] = new Artikel();
    });*/
    return <ArtikelExt[]> artikels;
  }

  toGruppExts(grupps: Array<Grupp>): GruppExt[] {
    return <GruppExt[]> grupps;
  }

  private toGroupExt(artikel: Grupp): GruppExt {
    return <GruppExt> artikel;
  }

  removeGrupp(flik: Flik, grupp: GruppExt) {
    flik.grupps.splice(flik.grupps.indexOf(grupp));
  }

  toFlikExts(fliks: Array<Flik>): FlikExt[] {
    return <FlikExt[]> fliks;
  }

  removeFlik(flik: FlikExt) {
    this.fliks.splice(this.fliks.indexOf(flik));
  }

}

class FlikExt extends Flik {
  removeable: boolean = false;
  pdArtikelsCount: number;
}

class GruppExt extends Grupp {
  removeable: boolean = false;
  pdArtikelsCount: number;
}

class ArtikelExt extends Artikel {
  editable: boolean = false;
  pdArtikelsCount: number;
}
