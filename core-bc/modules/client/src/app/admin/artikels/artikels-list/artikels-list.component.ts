import {Component, OnInit} from '@angular/core';
import {JwtHttp} from "../../../core/jwt-http";
import {Flik} from "../../../model/Flik";
import {Grupp} from "../../../model/Grupp";
import {Artikel} from "../../../model/Artikel";

@Component({
  selector: 'app-artikels-list',
  templateUrl: './artikels-list.component.html',
  styleUrls: ['./artikels-list.component.scss']
})
export class ArtikelsListComponent implements OnInit {

  constructor(private http: JwtHttp) {
  }

  fliks: Array<Flik>;

  public columns = ['name'];

  ngOnInit() {
    this.fetchDataFromServer();
  }

  fetchDataFromServer() {
    this.http.get('/api/flik').map(response => response.json()).subscribe(
      (fliks: Array<Flik>) => {
        this.fliks = fliks;
        console.log('fliks', fliks);
        const groups:Grupp[] = [];
        this.fliks.forEach(f => f.grupps.forEach(g => groups.push(g)));
        this.fetchArtikelUsageFromServer(groups);
      }
    );
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
        for (var i = 0; i < counts.length; i++)
          this.toArtikelExt(last.artikels[i]).pdArtikelsCount = counts[i];
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
    this.http.put('/api/flik/list', this.fliks).map(response => response.json()).subscribe(
      (fliks: Array<Flik>) => {
        this.fliks = fliks;
        console.log('saved-fliks', fliks);
      }
    );
  }

  toArtikelExts(artikels: Array<Artikel>): ArtikelExt[] {
    return <ArtikelExt[]> artikels;
  }
}

class FlikExt extends Flik {
  get gruppExts(): Grupp[] {
    var a = <Array<any>> this.grupps;
    return a;
  }

  pdArtikelsCount: number;
}

class GruppExt extends Grupp {
  get artikelExts(): ArtikelExt[] {
    var a = <Array<any>> this.artikels;
    return a;
  }

  pdArtikelsCount: number;
}

class ArtikelExt extends Artikel {
  pdArtikelsCount: number;
}
