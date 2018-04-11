import {Component, Input, OnInit} from '@angular/core';
import {Patient} from "../../model/Patient";
import {Pd} from "../../model/Pd";

@Component({
  selector: 'requisition-edit',
  templateUrl: './requisition-edit.component.html',
  styleUrls: ['./requisition-edit.component.scss']
})
export class RequisitionEditComponent implements OnInit {

  constructor() {

  }

  @Input() pd: Pd;

  @Input() patientId: number;



  ngOnInit() {

  }

}
