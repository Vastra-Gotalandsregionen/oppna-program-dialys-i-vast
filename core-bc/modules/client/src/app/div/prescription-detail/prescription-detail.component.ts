import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Patient} from "../../model/Patient";
import {JwtHttp} from "../../core/jwt-http";
import {BestRad} from "../../model/BestRad";
import {MatTableDataSource} from "@angular/material";


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

  print(): void {
      let printContents, popupWin;
      printContents = document.getElementById('bastallningsTableWrap').innerHTML;
      popupWin = window.open('', '_blank', 'top=0,left=0,height=100%,width=auto');
      popupWin.document.open();
      popupWin.document.write(`
        <html>
          <head>
            <title>Beställning</title>
            <style>
              .mat-table {
                  display: block;
              }

              .mat-header-row,
              .mat-row {
                  align-items: center;
                  border-bottom: 1px solid #333;
                  box-sizing: border-box;
                  display: flex;
                  padding: 0 24px;
              }

              .mat-header-cell {
                  color: #555;
                  font-size: 12px;
                  font-weight: bold;
              }

              .mat-cell,
              .mat-header-cell {
                  flex: 1;
                  overflow: hidden;
                  padding: 10px;
                  word-wrap: break-word;
              }

              .mat-table .mat-cell {
              	 font-size: 14px;
                 overflow: visible;
                 color: #333;
              }

            </style>
          </head>
      <body onload="window.print();window.close()">${printContents}</body>
        </html>`
      );
      popupWin.document.close();
  }


}
