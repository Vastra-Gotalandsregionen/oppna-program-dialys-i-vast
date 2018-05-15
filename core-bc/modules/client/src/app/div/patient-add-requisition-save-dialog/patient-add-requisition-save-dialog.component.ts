import {Component, Inject, OnInit, Optional} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";

@Component({
  selector: 'app-patient-add-requisition-save-dialog',
  templateUrl: './patient-add-requisition-save-dialog.component.html',
  styleUrls: ['./patient-add-requisition-save-dialog.component.scss']
})
export class PatientAddRequisitionSaveDialogComponent implements OnInit {

  public dialogRef: MatDialogRef<PatientAddRequisitionSaveDialogComponent>;

  constructor(@Optional() dialogRef: MatDialogRef<PatientAddRequisitionSaveDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
    this.dialogRef = dialogRef;
  }

  ngOnInit() {

  }

}
