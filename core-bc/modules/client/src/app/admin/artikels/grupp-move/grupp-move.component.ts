import {Component, Inject, OnInit, Optional} from '@angular/core';
import {MottagningsDialogComponent} from "../../mottagnings/mottagnings-dialog/mottagnings-dialog.component";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {Mottagning} from "../../../model/Mottagning";
import {FlikRot} from "../../../model/FlikRot";

@Component({
  selector: 'app-grupp-move',
  templateUrl: './grupp-move.component.html',
  styleUrls: ['./grupp-move.component.scss']
})
export class GruppMoveComponent implements OnInit {

  public dialogRef: MatDialogRef<GruppMoveComponent>;

  constructor(@Optional() dialogRef: MatDialogRef<GruppMoveComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
    this.dialogRef = dialogRef;
    if (!data.flikRot) console.log("Data object for grupp-move should contain a flikRoot property.");
    if (!data.item2move) console.log("Data object for grupp-move should contain a item2move property.");
    if (!data.previousFlik) console.log("Data object for grupp-move should contain a previousFlik property.");
  }

  ngOnInit() {

  }

}
