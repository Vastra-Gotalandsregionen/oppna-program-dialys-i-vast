import {Component, Input, OnInit} from '@angular/core';
import {Patient} from "../../model/Patient";
import {ActivatedRoute, Router} from "@angular/router";
import {JwtHttp} from "../../core/jwt-http";
import {Ansvarig} from "../../model/Ansvarig";
import {Mottagning} from "../../model/Mottagning";
import {MatSnackBar} from "@angular/material";

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

  constructor(private route: ActivatedRoute, private http: JwtHttp, private router: Router, private snackBar: MatSnackBar) {

  }

  ngOnInit() {
    this.patient = new Patient();
    this.patient.ansvarig = new Ansvarig();
    const id = this.route.snapshot.paramMap.get('id');
    console.log("The id was " + id);
    if (id === 'create')
      this.fetchReferencedData();
    else
      this.fetchData(id);
  }

  fetchData(id: string) {
    this.http.get('/api/patient/' + id)
      .map(response => response.json())
      .subscribe((incommingPatient: Patient) => {
        this.patient = incommingPatient;
        this.fetchReferencedData();
      });
  }

  fetchReferencedData() {
    this.http.get('/api/mottagning')
      .map(response => response.json())
      .subscribe((incommingMottagnings: Array<Mottagning>) => {
        this.mottagnings = incommingMottagnings;
        console.log(incommingMottagnings);
        this.mottagnings.forEach(
          item => this.mottagningById.set(item.id, item)
        );
      });

  }

  compareAnsvarigs(a1: Ansvarig, a2: Ansvarig): boolean {
    return a1.id === a2.id;
  }

  saveToServerSide() {
    this.http.put('/api/patient', this.patient).map(response => response.json()).subscribe(
      (updated: Patient) => {
        console.log('Saved', updated);
        this.snackBar.open('Lyckades spara!', null, {duration: 3000})
          .afterDismissed().subscribe(() => {
          if (!this.patient.id) {
            this.patient.id = updated.id;
            this.router.navigate(['/patienter/' + updated.id + '/edit']);
          }
        });
      }
    );
  }

}
