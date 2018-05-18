import { Component, OnInit } from '@angular/core';
import {RequisitionDataService} from "../services/requisition-data.service";
import {Pd} from "../../model/Pd";
import {ActivatedRoute} from "@angular/router";
import {Patient} from "../../model/Patient";
import {JwtHttp} from "../../core/jwt-http";
import {PDArtikel} from "../../model/PDArtikel";
import {MatTableDataSource} from "@angular/material";
import {Util} from "../../core/util/util";

@Component({
  selector: 'app-requisition-view',
  templateUrl: './requisition-view.component.html',
  styleUrls: ['./requisition-view.component.scss']
})
export class RequisitionViewComponent implements OnInit {

  constructor(public reqDataService: RequisitionDataService,
              private rout: ActivatedRoute,
              protected http: JwtHttp) { }

  pd: Pd;
  patientId: number;
  patient: Patient;
  datasource = new MatTableDataSource<PDArtikel>();
  displayedColumns = ['artikel', 'mangd', 'artikelnr'];
  ngOnInit() {
    this.patientId = +this.rout.snapshot.params['id'];
    this.pd = this.reqDataService.pdsToPrint;
    if (this.patientId) {

      const $data2 = this.http.get('/api/patient/' + this.patientId).map(response => response.json());

      $data2.subscribe((data: Patient) => {
        this.patient = data;
      });
      this.datasource.data = this.pd.pdArtikels;
    }
  }
  print(title: string, printNodeId: string): boolean {
    let printContents = document.getElementById(printNodeId).innerHTML;
    return Util.print(title, printContents);
  }


}
