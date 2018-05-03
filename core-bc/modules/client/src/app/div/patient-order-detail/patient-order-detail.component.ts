import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Patient} from "../../model/Patient";
import {JwtHttp} from "../../core/jwt-http";
import {BestRad} from "../../model/BestRad";
import {MatTableDataSource} from "@angular/material";
import {Util} from "../../core/util/util";


@Component({
  selector: 'app-patient-order-detail',
  templateUrl: './patient-order-detail.component.html',
  styleUrls: ['./patient-order-detail.component.scss']
})
export class PatientOrderDetailComponent implements OnInit {

  bestallningsId: number;
  pnr: string;
  rekvisDatum: Date;
  rekvisId: string;
  id: string;
  fornamn: string;
  efternamn: string;
  dataSources = new MatTableDataSource<BestRad>();
  displayedColumns = ['artikel', 'mangd', 'pdartikel', 'antal'];
  constructor(private route:ActivatedRoute, protected http: JwtHttp) {}

  ngOnInit() {
    this.bestallningsId = this.route.snapshot.params['id'];
    this.rekvisDatum = this.route.snapshot.queryParams['rekvisDatum'];
    this.rekvisId = this.route.snapshot.queryParams['rekvisitionid'];
    this.id = this.route.snapshot.queryParams['patId'];


    const $data = this.http.get('/api/bestallning/' + this.bestallningsId).map(response => response.json());

    $data.subscribe((data: BestRad[]) => {
      this.dataSources.data = data;
      if (this.id) {

        const $data2 = this.http.get('/api/patient/' + this.id).map(response => response.json());

        $data2.subscribe((data: Patient) => {
          this.pnr = data.pnr;
          this.fornamn = data.fornamn;
          this.efternamn = data.efternamn;
        });
      }
      //this.bestallningsRader = data;
    });
  }

  print(title: string, printNodeId: string): boolean {
    let printContents = document.getElementById(printNodeId).innerHTML;
    return Util.print(title, printContents);
  }

}
