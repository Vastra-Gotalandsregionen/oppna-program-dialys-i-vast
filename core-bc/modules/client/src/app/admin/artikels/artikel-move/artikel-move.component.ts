import {Component, Inject, OnInit, Optional} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {GruppMoveComponent} from "../grupp-move/grupp-move.component";

@Component({
  selector: 'app-artikel-move',
  templateUrl: './artikel-move.component.html',
  styleUrls: ['./artikel-move.component.scss']
})
export class ArtikelMoveComponent implements OnInit {

  public dialogRef: MatDialogRef<ArtikelMoveComponent>;

  constructor(@Optional() dialogRef: MatDialogRef<ArtikelMoveComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
    this.dialogRef = dialogRef;
    if (!data.flikRot) console.log("Data object for grupp-move should contain a flikRoot property.");
    if (!data.item2move) console.log("Data object for grupp-move should contain a item2move property.");
    if (!data.previousGrupp) console.log("Data object for grupp-move should contain a previousFlik property.");
  }

  ngOnInit() {

  }

}
