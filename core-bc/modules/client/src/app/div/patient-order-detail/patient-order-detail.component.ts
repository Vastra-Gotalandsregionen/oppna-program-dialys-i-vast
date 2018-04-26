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
  gata: string;
  postNummer: string;
  postOrt: string;
  fornamn: string;
  efternamn: string;
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
    this.gata = this.route.snapshot.queryParams['gatuadress'];
    this.postOrt = this.route.snapshot.queryParams['postort'];
    this.postNummer = this.route.snapshot.queryParams['postnummer'];
    this.fornamn = this.route.snapshot.queryParams['namn'];
    this.efternamn = this.route.snapshot.queryParams['efternamn'];



    const $data = this.http.get('/api/bestallning/' + this.bestallningsId).map(response => response.json());

    $data.subscribe((data: BestRad[]) => {
      this.dataSources.data = data;
      //this.bestallningsRader = data;
    });
  }

  print(title: string, printNodeId: string): boolean {
    let printContents = document.getElementById(printNodeId).innerHTML;
    return Util.print(title, printContents);
  }

}
