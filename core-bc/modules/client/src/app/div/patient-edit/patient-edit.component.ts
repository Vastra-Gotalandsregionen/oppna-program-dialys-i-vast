import {Component, Input, OnInit} from '@angular/core';
import {Patient} from "../../model/Patient";
import {ActivatedRoute} from "@angular/router";
import {Observable} from "rxjs/Observable";
import {Response} from "@angular/http";
import {User} from "../../model/user";
import {HttpClient} from "@angular/common/http";
import {Link} from "../../model/link";
import {JwtHttp} from "../../core/jwt-http";
import {Ansvarig} from "../../model/Ansvarig";
import {Mottagning} from "../../model/Mottagning";

@Component({
  selector: 'app-patient-edit',
  templateUrl: './patient-edit.component.html',
  styleUrls: ['./patient-edit.component.scss']
})
export class PatientEditComponent implements OnInit {

  @Input() patient: Patient;

  @Input() ansvarigs: Array<Ansvarig> = [];

  @Input() ansvarigsDomain: Array<Ansvarig> = [];

  @Input() mottagnins: Array<Mottagning> = [];

  @Input() selectedMottagning: Mottagning;
  @Input() selectedAnsvarig: Ansvarig;

  @Input() mottagningById: Map<number, Mottagning> = new Map<number, Mottagning>();


  constructor(private route: ActivatedRoute, private http: JwtHttp) {

  }

  ngOnInit() {
    this.patient = new Patient();
    const id = this.route.snapshot.paramMap.get('id');
    console.log('id -> ' + id);
    this.fetchData(id);
  }

  fetchData(id: string) {
    this.http.get('/api/patient/' + id)
      .map(response => response.json())
      .subscribe((incommingPatient: Patient) => {
        this.patient = incommingPatient;
      });

    this.http.get('/api/ansvarig')
      .map(response => response.json())
      .subscribe((incommingAnsvarigs: Array<Ansvarig>) => {
        this.ansvarigs = incommingAnsvarigs;

        this.http.get('/api/mottagning')
          .map(response => response.json())
          .subscribe((incommingMottagnings: Array<Mottagning>) => {
            this.mottagnins = incommingMottagnings;
            for (let mottagning of this.mottagnins) {
              this.mottagningById.set(mottagning.id, mottagning);
            }
            for (let ansvarig of this.ansvarigs) {
              let item = this.mottagningById.get(ansvarig.mottagningID);
              if (!item.ansvarigs) item.ansvarigs = [];
              item.ansvarigs.push(ansvarig);
            }
            if (this.patient.ansvarig) {
              this.ansvarigsDomain = this.mottagningById.get(this.patient.ansvarig.mottagningID).ansvarigs;
            }
            this.selectedAnsvarig = this.patient.ansvarig;
            this.selectedMottagning = this.mottagningById.get(this.selectedAnsvarig.mottagningID);
          });

      });
  }

  onMottagningSelect() {
    if (this.selectedMottagning) {
      console.log("Selected ", this.selectedMottagning);
      if(this.selectedMottagning.ansvarigs) {
        //this.patient.ansvarig = this.selectedMottagning.ansvarigs[0];
        this.ansvarigsDomain = this.selectedMottagning.ansvarigs;
        this.selectedAnsvarig = this.selectedMottagning.ansvarigs[0];
        this.onAnsvarigChange();
      }
    }
  }

  onAnsvarigChange() {
    console.log("Ansvarig in patient");
    this.patient.ansvarig = this.selectedAnsvarig;
  }
}
