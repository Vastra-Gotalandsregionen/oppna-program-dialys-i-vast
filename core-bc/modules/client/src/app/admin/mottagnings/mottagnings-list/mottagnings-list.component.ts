import {Component, OnInit} from '@angular/core';
import {Mottagning} from "../../../model/Mottagning";
import {JwtHttp} from "../../../core/jwt-http";
import {MatDialog} from "@angular/material";
import {ConfirmDialogComponent} from "../../../shared/confirm-dialog/confirm-dialog.component";
import {InputDialogComponent} from "../../../shared/input-dialog/input-dialog.component";

@Component({
  selector: 'app-mottagnings-list',
  templateUrl: './mottagnings-list.component.html',
  styleUrls: ['./mottagnings-list.component.scss']
})
export class MottagningsListComponent implements OnInit {

  constructor(private http: JwtHttp, private dialog: MatDialog) {
  }

  displayedColumns = ['id', 'name', 'personnelCount', 'patientCount', 'menu'];

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
          console.log("got data ", this.mottagnings);
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
    let dialogRef = this.dialog.open(InputDialogComponent, {
      data: {
        text: 'Ändra namn på mottagning' + mottagning.namn + '?',
        value: mottagning.namn,
        confirmButtonText: 'Ändra',
      },
      panelClass: 'apk-dialog'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result != null) {
        mottagning.namn = result;
        this.http.put('/api/mottagning/', mottagning)
          .subscribe(response => {
            console.log(response);
            this.fetchListItems();
          });
      }
    });
  }

  createNewMottagning() {
    const mottagning: Mottagning = new Mottagning();
    let dialogRef = this.dialog.open(InputDialogComponent, {
      data: {
        text: 'Ange namn på ny mottagning',
        value: mottagning.namn,
        confirmButtonText: 'Skapa',
      },
      panelClass: 'apk-dialog'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result != null) {
        mottagning.namn = result;
        this.http.put('/api/mottagning/', mottagning)
          .subscribe(response => {
            console.log(response);
            this.fetchListItems();
          });
      }
    });
  }

}

class MottagningExt extends Mottagning {
  personalCount: number;
  patientCount: number;
}
