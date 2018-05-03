import {Component, OnInit} from '@angular/core';
import {Mottagning} from "../../../model/Mottagning";
import {JwtHttp} from "../../../core/jwt-http";
import {MatDialog} from "@angular/material";
import {ConfirmDialogComponent} from "../../../shared/confirm-dialog/confirm-dialog.component";
import {InputDialogComponent} from "../../../shared/input-dialog/input-dialog.component";
import {MottagningsDialogComponent} from "../mottagnings-dialog/mottagnings-dialog.component";

@Component({
  selector: 'app-mottagnings-list',
  templateUrl: './mottagnings-list.component.html',
  styleUrls: ['./mottagnings-list.component.scss']
})
export class MottagningsListComponent implements OnInit {

  constructor(private http: JwtHttp, private dialog: MatDialog) {
  }

  displayedColumns = ['id', 'name', 'status', 'personnelCount', 'patientCount', 'menu'];

  mottagnings: MottagningExt[];

  ngOnInit() {
    console.log('MottagningsListComponent');
    this.fetchListItems();
  }

  fetchListItems() {
    this.http.get('/api/mottagning').map(response => response.json())
      .subscribe(
        (mottagnings: any) => {
          this.mottagnings = mottagnings;
          var url: string = '/api/generic/counts/Mottagning/users/iids/';
          var ids = '';
          this.mottagnings.forEach((i) => ids += 'i' + i.id);
          this.http.get(url + ids)
            .map(response => response.json())
            .subscribe((counts: Array<number>) => {
              for (var c = 0; c < counts.length; c++) this.mottagnings[c].personalCount = counts[c];
            });
          url = '/api/generic/counts/Mottagning/patients/iids/';
          this.http.get(url + ids)
            .map(response => response.json())
            .subscribe((counts: Array<number>) => {
              for (var c = 0; c < counts.length; c++) this.mottagnings[c].patientCount = counts[c];
            });
        }
      );
  }


  confirmDelete(mottagning: Mottagning) {
    let dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        text: 'Är du säker att du vill ta bort ' + mottagning.namn + '?',
        confirmButtonText: 'Ta bort'
      },
      panelClass: 'apk-dialog'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 'confirm') {
        this.http.delete('/api/mottagning/' + mottagning.id)
          .subscribe(response => {
            console.log(response);
            this.fetchListItems();
          });
      }
    });
  }

  change(mottagning: Mottagning) {
    mottagning = Object.assign({}, mottagning);
    let dialogRef = this.dialog.open(MottagningsDialogComponent, {
      data: mottagning,
      panelClass: 'apk-dialog'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result != null) {
        this.http.put('/api/mottagning/', result)
          .subscribe(response => {
            console.log(response);
            this.fetchListItems();
          });
      }
    });
  }

  createNewMottagning() {
    const mottagning: Mottagning = new Mottagning();
    this.change(mottagning);
  }

}

class MottagningExt extends Mottagning {
  personalCount: number;
  patientCount: number;
}
