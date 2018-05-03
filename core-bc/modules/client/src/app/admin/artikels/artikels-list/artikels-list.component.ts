import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {JwtHttp} from "../../../core/jwt-http";
import {Flik} from "../../../model/Flik";
import {Grupp} from "../../../model/Grupp";
import {Artikel} from "../../../model/Artikel";
import {FlikRot} from "../../../model/FlikRot";
import {ActivatedRoute} from "@angular/router";
import {MatDialog, MatDialogRef, MatTableDataSource} from "@angular/material";
import {GruppMoveComponent} from "../grupp-move/grupp-move.component";

@Component({
  selector: 'app-artikels-list',
  templateUrl: './artikels-list.component.html',
  styleUrls: ['./artikels-list.component.scss']
})
export class ArtikelsListComponent implements OnInit {

  constructor(protected route: ActivatedRoute,
              private http: JwtHttp,
              private changeDetectorRefs: ChangeDetectorRef,
              private dialog: MatDialog) {
  }

  fliks: Array<Flik>;

  flikRot: FlikRot;

  typ: string;

  public columns = ['name'];

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.typ = params.typ;
      this.fetchDataFromServer();
    });
  }

  fetchDataFromServer() {
    this.http.get('/api/flikrot/' + this.typ).map(response => response.json()).subscribe(
      (flikRot: FlikRot) => {
        this.flikRot = flikRot;
        this.fliks = flikRot.fliks;
        console.log('fliks', this.fliks);
        const groups: Grupp[] = [];
        this.fliks.forEach(f => f.grupps.forEach(g => groups.push(g)));
        this.fetchArtikelUsageFromServer(groups);
        this.startFetchGruppsArtikelUsageFromServer();
        this.fetchFliksArtikelUsageFromServer(this.fliks);
      }
    );
  }

  startFetchGruppsArtikelUsageFromServer() {
    const groups: Grupp[] = [];
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

  fetchFliksArtikelUsageFromServer(fliks: Flik[]) {
    if (fliks.length === 0) return;
    var ids: number[] = [];
    fliks.forEach((i) => ids.push(i.id));
    if (ids.length === 0) return;

    var url = '/api/generic/counts/Flik/grupps.artikels.pdArtikels/iids/' + ids;
    this.http.get(url).map(response => response.json()).subscribe(
      (counts: number[]) => {
        for (var i = 0; i < counts.length; i++) {
          this.toFlikExt(fliks[i]).pdArtikelsCount = counts[i];
          this.toFlikExt(fliks[i]).removeable = (counts[i] === 0);
        }
      });
  }

  fetchArtikelUsageFromServer(groups: Grupp[]) {
    if (groups.length === 0) return;
    var ids: number[] = [];
    const last = groups[groups.length - 1];
    groups.splice(groups.length - 1, 1);
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
    var flik: FlikExt = new FlikExt();
    flik.removeable = true;
    this.fliks.unshift(flik);
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

  private toFlikExt(flik: Flik): FlikExt {
    return <FlikExt> flik;
  }

  removeGrupp(flik: Flik, grupp: GruppExt) {
    flik.grupps.splice(flik.grupps.indexOf(grupp), 1);
  }

  toFlikExts(fliks: Array<Flik>): FlikExt[] {
    return <FlikExt[]> fliks;
  }

  removeFlik(flik: FlikExt) {
    this.fliks.splice(this.fliks.indexOf(flik), 1);
  }

  createNewGrupp(flik: FlikExt) {
    var grupp: GruppExt = new GruppExt();
    grupp.removeable = true;
    flik.grupps.unshift(grupp);
  }

  createNewArtikel(grupp: GruppExt) {
    const artikels = grupp.artikels;
    var artikel: ArtikelExt = new ArtikelExt();
    artikel.editable = true;
    artikels.unshift(artikel);
    grupp.artikels = artikels;
    this.changeDetectorRefs.detectChanges();
  }

  deleteArtikel(grupp: GruppExt, artikel) {
    grupp.artikels.splice(grupp.artikels.indexOf(artikel), 1);
  }

  toArtikelExtsModel(artikels: Array<Artikel>): MatTableDataSource<ArtikelExt> {
    return new MatTableDataSource<ArtikelExt>(this.toArtikelExts(artikels));
  }

  // dialogRef:MatDialogRef<GruppMoveComponent>;

  openMoveGruppDialog(previousFlik: Flik, grupp: Grupp) {
    let dialogRef = this.dialog.open(GruppMoveComponent, {
      data: {
        flikRot: this.flikRot,
        item2move: grupp,
        previousFlik: previousFlik,
        moveGrupp(that: Grupp, from: Flik, to: Flik) {
          from.grupps.splice(from.grupps.indexOf(that), 1);
          to.grupps.push(that);
          dialogRef.close(null);
        }
      },
      panelClass: 'apk-dialog'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result != null) {
        console.log(result);
        result.previousRot.grupps.splice(
          result.previousFlik.grupps.indexOf(result.item2move), 1
        );
      }
    });
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
