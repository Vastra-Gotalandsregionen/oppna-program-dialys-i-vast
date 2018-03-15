import {Component, Input, OnInit} from '@angular/core';
import {Patient} from "../../model/Patient";
import {ActivatedRoute} from "@angular/router";
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

  @Input() mottagnings: Array<Mottagning> = [];

  @Input() selectedMottagning: Mottagning;
  @Input() selectedAnsvarig: Ansvarig;

  @Input() mottagningById: Map<number, Mottagning> = new Map<number, Mottagning>();

  constructor(private route: ActivatedRoute, private http: JwtHttp) {

  }

  ngOnInit() {
    this.patient = new Patient();
    this.patient.ansvarig = new Ansvarig();
    const id = this.route.snapshot.paramMap.get('id');
    console.log('id -> ' + id);
    this.fetchData(id);
  }

  fetchData(id: string) {
    this.http.get('/api/patient/' + id)
      .map(response => response.json())
      .subscribe((incommingPatient: Patient) => {
        this.patient = incommingPatient;
        console.log(this.patient);

        this.http.get('/api/mottagning')
          .map(response => response.json())
          .subscribe((incommingMottagnings: Array<Mottagning>) => {
            this.mottagnings = incommingMottagnings;
            console.log(incommingMottagnings);
            this.mottagnings.forEach(
              item => this.mottagningById.set(item.id, item)
            );
          });

      });
  }

  compareAnsvarigs(a1: Ansvarig, a2: Ansvarig): boolean {
    return a1.id === a2.id;
  }

  saveToServerSide() {
    this.http.put('/api/patient', this.patient).map(response => response.json()).subscribe(
      (updated: Patient) => {
        console.log('Saved', updated);
      }
    );
  }
}
