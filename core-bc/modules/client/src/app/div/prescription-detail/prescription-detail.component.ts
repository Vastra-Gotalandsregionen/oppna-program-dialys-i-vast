import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-prescription-detail',
  templateUrl: './prescription-detail.component.html',
  styleUrls: ['./prescription-detail.component.scss']
})
export class PrescriptionDetailComponent implements OnInit {

  bestallningsId: number;
  pnr: string;
  rekvisDatum: Date;
  rekvisId: string;
  constructor(private route:ActivatedRoute) {}

  ngOnInit() {
    this.bestallningsId = this.route.snapshot.params['id'];
    this.pnr = this.route.snapshot.queryParams['persnr'];
    this.rekvisDatum = this.route.snapshot.queryParams['rekvisDatum'];
    this.rekvisId = this.route.snapshot.queryParams['rekvisitionid'];

  }

}
