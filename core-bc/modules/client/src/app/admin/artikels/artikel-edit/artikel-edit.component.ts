import {Component, Inject, OnInit, Optional} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";

@Component({
  selector: 'app-artikel-edit',
  templateUrl: './artikel-edit.component.html',
  styleUrls: ['./artikel-edit.component.scss']
})
export class ArtikelEditComponent implements OnInit {

  public dialogRef: MatDialogRef<ArtikelEditComponent>;

  constructor(@Optional() dialogRef: MatDialogRef<ArtikelEditComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
    this.dialogRef = dialogRef;
    if (!data.artikel) console.log("Data object for grupp-move should contain a artikel property.");
  }

  ngOnInit() {

  }

}
