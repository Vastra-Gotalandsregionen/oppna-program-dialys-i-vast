import {Component, Optional, Inject} from '@angular/core';
import {MatDialogRef} from '@angular/material';
import {MAT_DIALOG_DATA} from '@angular/material';

@Component({
  styleUrls: ['./input-dialog.component.css'],
  templateUrl: './input-dialog.component.html'
})
export class InputDialogComponent {

  public dialogRef: MatDialogRef<InputDialogComponent>;

  constructor(@Optional() dialogRef: MatDialogRef<InputDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
    this.dialogRef = dialogRef;
  }

}

