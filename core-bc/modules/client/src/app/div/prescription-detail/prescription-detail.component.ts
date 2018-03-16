import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Patient} from "../../model/Patient";
import {JwtHttp} from "../../core/jwt-http";
import {BestPDRad} from "../../model/BestPDRad";
import {Observable} from 'rxjs/Observable';
import {BestInfo} from "../../model/BestInfo";


@Component({
  selector: 'app-prescription-detail',
  templateUrl: './prescription-detail.component.html',
  styleUrls: ['./prescription-detail.component.scss']
})
export class PrescriptionDetailComponent implements OnInit {

  data:Patient;
  bestallningDataSource: BestPDRad[];
  bestallningsId: number;
  pnr: string;
  rekvisDatum: Date;
  rekvisId: string;
  id: string;
  rekvisIndex: number;
  rekvisFound: boolean = false;
  bestallningFound: boolean = false;
  bestallningIndex: number;
  bestallningsInfo: BestInfo[];
  displayedColumns = ['artikel', 'mangd', 'pdartikel', 'antal'];
  constructor(private route:ActivatedRoute, protected http: JwtHttp) {}

  ngOnInit() {
    this.bestallningsId = this.route.snapshot.params['id'];
    this.pnr = this.route.snapshot.queryParams['persnr'];
    this.rekvisDatum = this.route.snapshot.queryParams['rekvisDatum'];
    this.rekvisId = this.route.snapshot.queryParams['rekvisitionid'];
    this.id = this.route.snapshot.queryParams['patId'];

    if (this.id) {
      const $data = this.http.get('/api/patient/' + this.id)
        .map(response => response.json())
        .share();

      $data.subscribe((data: Patient) => {
        this.data = data;
        for ( let i=0; i< data.pds.length; i++){
          if (data.pds[i].id === +this.rekvisId){
            this.rekvisIndex = i;
            this.rekvisFound = true;
            this.bestallningsInfo = data.pds[this.rekvisIndex].bestInfos;
            break;
          }
        }
        if (this.rekvisFound)
        {
          for ( let i=0; i<this.bestallningsInfo.length; i++){
            if (this.bestallningsInfo[i].id === +this.bestallningsId){
              this.bestallningIndex = i;
              this.bestallningFound = true;
              this.bestallningDataSource = this.bestallningsInfo[this.bestallningIndex].bestPDRads;
              break;
            }
          }
        }
      });
    }
  }
}
