import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Patient} from "../../model/Patient";
import {JwtHttp} from "../../core/jwt-http";
import {BestRad} from "../../model/BestRad";
import {MatTableDataSource} from "@angular/material";
import {Util} from "../../core/util/util";


@Component({
  selector: 'app-prescription-detail',
  templateUrl: './prescription-detail.component.html',
  styleUrls: ['./prescription-detail.component.scss']
})
export class PrescriptionDetailComponent implements OnInit {

  data:Patient;
  bestallningsId: number;
  pnr: string;
  rekvisDatum: Date;
  rekvisId: string;
  id: string;
  bestallningsRader: BestRad[];
  dataSources = new MatTableDataSource<BestRad>();
  displayedColumns = ['artikel', 'mangd', 'pdartikel', 'antal'];
  constructor(private route:ActivatedRoute, protected http: JwtHttp) {}

  ngOnInit() {
    this.bestallningsId = this.route.snapshot.params['id'];
    this.pnr = this.route.snapshot.queryParams['persnr'];
    this.rekvisDatum = this.route.snapshot.queryParams['rekvisDatum'];
    this.rekvisId = this.route.snapshot.queryParams['rekvisitionid'];
    this.id = this.route.snapshot.queryParams['patId'];



    const $data = this.http.get('/api/bestallning/' + this.bestallningsId).map(response => response.json());

    $data.subscribe((data: BestRad[]) => {
      this.dataSources.data = data;
      //this.bestallningsRader = data;
    });
  }

  print(title: String, printNodeId: String): void {
    let printContents = document.getElementById(printNodeId).innerHTML;
    Util.print(title, printContents);
  }

}
