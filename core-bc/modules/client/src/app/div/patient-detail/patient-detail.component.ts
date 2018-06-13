import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Patient} from '../../model/Patient';
import {AuthService} from '../../core/auth/auth.service';
import {JwtHttp} from "../../core/jwt-http";
import {BestInfo} from "../../model/BestInfo";
import {Pd} from "../../model/Pd";
import {MatPaginator, MatTableDataSource} from "@angular/material";
import {BestRad} from "../../model/BestRad";

@Component({
  selector: 'app-dialys-detail',
  templateUrl: './patient-detail.component.html',
  styleUrls: ['./patient-detail.component.css']
})
export class PatientDetailComponent implements OnInit {

  headerdata: Pd;
  giltigRekvisitionFinns: boolean;
  pausadPatient: boolean;
  pdRekviser: Pd[];
  hdRekviser: Pd[];
  id: string;
  patTyp: string;
  data: Patient;
  showOldRequisitions: boolean;
  gamlarekvisfinns: boolean;
  dataSource1 = new MatTableDataSource<BestInfo>();
  panelOpenState: Number[] = [];
  @ViewChild('page1') page1 : MatPaginator;

  constructor(protected route: ActivatedRoute,
              protected http: JwtHttp,
              public authService: AuthService) {
  }

  ngOnInit() {

    this.route.params.subscribe(params => {
      this.id = params.id;

      this.showOldRequisitions = false;

      if (this.id) {
        const $data = this.http.get('/api/patient/' + this.id)
          .map(response => response.json())
          .share();

        $data.subscribe((data: Patient) => {
          this.data = data;
          Patient.init(this.data);
          this.data.sortPds();
          this.patTyp = data.typ;
          this.pausadPatient = data.status.trim().toLowerCase() != 'aktiv'
          if (this.patTyp == 'PD')
          {
            this.pdRekviser = data.pds.filter(item => item.typ == 'PD')
            if (this.pdRekviser.length != 0)
            {
              this.giltigRekvisitionFinns = true;
              setTimeout(() => this.dataSource1.paginator = this.page1);
              this.dataSource1.data = this.pdRekviser[0].bestInfos;
              this.headerdata = this.pdRekviser[0];
              if(this.data.pds.length > 1){
                this.data.pds = this.data.pds.filter(item => item.id != this.pdRekviser[0].id);
                this.gamlarekvisfinns = true;
              }
              else
              {
                this.gamlarekvisfinns = false;
              }
            }
            else
            {
              this.giltigRekvisitionFinns = false;
            }
          }
          else if(this.patTyp == 'HD')
          {
            this.hdRekviser = data.pds.filter(item => item.typ == 'HD')
            if (this.hdRekviser.length != 0)
            {
              this.giltigRekvisitionFinns = true;
              setTimeout(() => this.dataSource1.paginator = this.page1);
              this.dataSource1.data = this.hdRekviser[0].bestInfos;
              this.headerdata = this.hdRekviser[0];
              if(this.data.pds.length > 1){
                this.data.pds = this.data.pds.filter(item => item.id != this.hdRekviser[0].id)
                this.gamlarekvisfinns = true;
              }
              else
              {
                this.gamlarekvisfinns = false;
              }
            }
            else
            {
              this.giltigRekvisitionFinns = false;
            }
          }

        }
        );
      }
    });
  }

  protected getId() {
    return this.id;
  }

  userHasEditPermission(data: Patient) {
    return this.authService.userHasDataEditPermission(data);
  }
}
