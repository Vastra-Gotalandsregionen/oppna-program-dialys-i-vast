import {Component, Optional, Inject} from '@angular/core';
import {MatDialogRef} from '@angular/material';
import {MAT_DIALOG_DATA} from '@angular/material';
import {Mottagning} from "../../../model/Mottagning";

@Component({
  styleUrls: ['./mottagnings-dialog.component.css'],
  templateUrl: './mottagnings-dialog.component.html'
})
export class MottagningsDialogComponent {

  public dialogRef: MatDialogRef<MottagningsDialogComponent>;

  constructor(@Optional() dialogRef: MatDialogRef<MottagningsDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: Mottagning) {
    this.dialogRef = dialogRef;
  }

}
