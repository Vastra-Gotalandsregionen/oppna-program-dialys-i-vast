import {Component, ElementRef, Input, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {Patient} from "../../model/Patient";
import {ActivatedRoute, Router} from "@angular/router";
import {JwtHttp} from "../../core/jwt-http";
import {Mottagning} from "../../model/Mottagning";
import {MatSnackBar} from "@angular/material";

@Component({
  selector: 'app-patient-edit',
  templateUrl: './patient-edit.component.html',
  styleUrls: ['./patient-edit.component.scss']
})
export class PatientEditComponent implements OnInit {

  @Input() patient: Patient;

  @Input() mottagnings: Array<Mottagning> = [];

  @ViewChild('pnrInput') pnrInput: ElementRef;

  @ViewChild('mottagningsHead') mottagningsHead: ElementRef;

  constructor(private route: ActivatedRoute, private http: JwtHttp, private router: Router, private snackBar: MatSnackBar) {

  }

  ngOnChanges(changes: SimpleChanges) {
    console.log(changes);
  }

  ngOnInit() {
    this.patient = new Patient();
    const id = this.route.snapshot.paramMap.get('id');
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
        /*console.log(incommingMottagnings);
        this.mottagnings.forEach(
          item => this.mottagningById.set(item.id, item)
        );*/
      });

  }

  saveToServerSide() {
    if (!this.checkRequiredFields()) {

      return;
    }
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

  checkRequiredFields(): boolean {
    if (!this.patient.pnr || this.patient.pnr.trim() === '') {
      this.abortShowErrorAndFocus('Personnummer måste vara ifylld.', this.pnrInput.nativeElement);
      return false;
    }
    if (this.patient.mottagnings.length == 0) {
      this.abortShowErrorAndFocus("Minst en mottagning måste vara vald.", this.mottagningsHead.nativeElement);

      return false;
    }
    return true;
  }

  private abortShowErrorAndFocus(personnummerMåsteVaraIfylld: string, nativeElement: any) {
    this.snackBar.open(personnummerMåsteVaraIfylld, null, {duration: 3000});
    setTimeout( () => {
      const domNode: HTMLElement = nativeElement;
      if (domNode.scrollIntoView)
        domNode.scrollIntoView();
      if (domNode.focus)
        domNode.focus();
    }, 3000);
  }

  /*private abortSave(andShowThis: string) {
    setTimeout( () => {
      this.pnrInput.nativeElement.focus();
    }, 3000);
  }*/

  public onMottagningChecked(item: Mottagning) {
    var index: number = -1;
    var i: number = 0;
    for (const mottagning of this.patient.mottagnings) {
      if (mottagning.id === item.id){
        index = i;
        break;
      }
      i++;
    }
    if (index > -1) {
      this.patient.mottagnings.splice(index, 1);
    } else {
      if (this.patient.mottagnings.indexOf(item) === -1)
        this.patient.mottagnings.push(item);
    }
  }

  public doesPatientHaveMottagning(item: Mottagning): boolean {
    if (!this.patient) return false;
    for (const mottagning of this.patient.mottagnings) {
      if (mottagning.id === item.id) return true;
    }
    return false;
  }

}
