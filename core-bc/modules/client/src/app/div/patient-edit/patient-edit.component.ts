import {Component, ElementRef, Input, OnInit, QueryList, SimpleChanges, ViewChild, ViewChildren} from '@angular/core';
import {Patient} from "../../model/Patient";
import {ActivatedRoute, Router} from "@angular/router";
import {JwtHttp} from "../../core/jwt-http";
import {Mottagning} from "../../model/Mottagning";
import {MatCheckbox, MatSnackBar} from "@angular/material";
import {AuthService} from "../../core/auth/auth.service";
import {User} from "../../model/user";

@Component({
  selector: 'app-patient-edit',
  templateUrl: './patient-edit.component.html',
  styleUrls: ['./patient-edit.component.scss']
})
export class PatientEditComponent implements OnInit {

  @Input() patient: Patient;

  @Input() mottagnings: Array<Mottagning> = [];

  @ViewChild('mottagningsHead') mottagningsHead: ElementRef;

  // 1
  @ViewChild('pnrInput') pnrInput: ElementRef;
  @ViewChild('fornamnInput') fornamnInput: ElementRef;
  @ViewChild('efternamnInput') efternamnInput: ElementRef;

  // 2
  @ViewChild('typInput') typInput: ElementRef;
  @ViewChild('leveransPaminnelseInput') leveransPaminnelseInput: ElementRef;
  @ViewChildren('mottagningInputs') mottagningInputs: QueryList<MatCheckbox>;

  // 3
  @ViewChild('adressInput') adressInput: ElementRef;
  @ViewChild('postNrInput') postNrInput: ElementRef;
  @ViewChild('postOrtInput') postOrtInput: ElementRef;
  @ViewChild('portkodInput') portkodInput: ElementRef;

  // 4
  @ViewChildren('tempAdressFrom') tempAdressFrom: ElementRef;
  @ViewChildren('tempAdressTomInput') tempAdressTomInput: ElementRef;
  @ViewChildren('tempAdress') tempAdress: ElementRef;
  @ViewChildren('tempPostNr') tempPostNr: ElementRef;
  @ViewChildren('tempPostOrt') tempPostOrt: ElementRef;

  // 5
  @ViewChild('telefonInput') telefonInput: ElementRef;
  @ViewChild('mobilInput') mobilInput: ElementRef;
  @ViewChild('epostInput') epostInput: ElementRef;

  // 6
  @ViewChild('utdelDagInput') utdelDagInput: ElementRef;
  @ViewChild('utdelTextInput') utdelTextInput: ElementRef;
  @ViewChild('utdelVeckaInput') utdelVeckaInput: ElementRef;

  // 7
  @ViewChildren('avropsOmbudInput') avropsOmbudInput: ElementRef;
  @ViewChildren('leveransMottagningsOmbudInput') leveransMottagningsOmbudInput: ElementRef;

  // 8
  @ViewChildren('ovrigtInput') ovrigtInput: ElementRef;

  // 9
  @ViewChildren('statusInput') statusInput

  private user: User;

  constructor(private route: ActivatedRoute,
              private http: JwtHttp,
              private router: Router,
              public authService: AuthService,
              private snackBar: MatSnackBar) {

  }

  ngOnInit() {
    this.patient = new Patient();
    const id = this.route.snapshot.paramMap.get('id');
    if (id === 'create')
      this.fetchReferencedData();
    else
      this.fetchData(id);

    this.http.get('/api/user/' + this.authService.getLoggedInUserId()).map(response => response.json()).subscribe(
      (u: User) => {
        this.user = u;
      }
    );
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
      });
  }

  checkMottagningsRequirements(): boolean {
    const patientMottganingsIds: Array<number> = [];
    const userMottganingsIds: Array<number> = [];
    this.user.mottagnings.forEach(i => userMottganingsIds.push(i.id));
    this.patient.mottagnings.forEach(i => patientMottganingsIds.push(i.id));
    for (const patsId of patientMottganingsIds) {
      if (userMottganingsIds.indexOf(patsId) > -1) {
        return true;
      }
    }

    this.snackBar.open('Du måste ha minst en mottagning gemensam med patienten.', null, {duration: 3000});
    setTimeout(() => {
      const domNode: HTMLElement = this.mottagningsHead.nativeElement;
      if (domNode.scrollIntoView)
        domNode.scrollIntoView();
      if (domNode.focus)
        domNode.focus();
    }, 3000);

    return false;
  }

  saveToServerSide() {
    if (!this.checkRequiredFields()) {
      return;
    }
    if (!this.checkMottagningsRequirements()) {
      return;
    }
    this.http.put('/api/patient', this.patient).map(response => response.json()).subscribe(
      (updated: Patient) => {
        console.log('Saved', updated);
        this.snackBar.open('Lyckades spara!', null, {duration: 3000})
          .afterDismissed().subscribe(() => {
          if (!this.patient.id) {
            this.patient.id = updated.id;
            this.router.navigate(['/patienter/', this.patient.id]);
          }
          else {
            this.router.navigate(['/patienter', this.patient.id]);
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
    if (!this.patient.fornamn || this.patient.fornamn.trim() === '') {
      this.abortShowErrorAndFocus('Förnamn måste vara ifylld.', this.fornamnInput.nativeElement);
      return false;
    }
    if (!this.patient.efternamn || this.patient.efternamn.trim() === '') {
      this.abortShowErrorAndFocus('Efternamn måste vara ifylld.', this.efternamnInput.nativeElement);
      return false;
    }
    if (this.patient.mottagnings.length == 0) {
      this.abortShowErrorAndFocus("Minst en mottagning måste vara vald.", this.mottagningsHead.nativeElement);
      return false;
    }

    if (!this.patient.adress || this.patient.adress.trim() === '') {
      this.abortShowErrorAndFocus('Adress måste vara ifylld.', this.adressInput.nativeElement);
      return false;
    }
    if (!this.patient.postOrt || this.patient.postOrt.trim() === '') {
      this.abortShowErrorAndFocus('Postort måste vara ifylld.', this.postOrtInput.nativeElement);
      return false;
    }
    if (!this.patient.postNr || this.patient.postNr.trim() === '') {
      this.abortShowErrorAndFocus('Postnummer måste vara ifylld.', this.postNrInput.nativeElement);
      return false;
    }
    if (!this.patient.telefon || this.patient.telefon.trim() === '') {
      this.abortShowErrorAndFocus('Telefonnummer måste vara ifylld.', this.telefonInput.nativeElement);
      return false;
    }
    return true;
  }

  private abortShowErrorAndFocus(personnummerMåsteVaraIfylld: string, nativeElement: any) {
    this.snackBar.open(personnummerMåsteVaraIfylld, null, {duration: 3000});
    setTimeout(() => {
      const domNode: HTMLElement = nativeElement;
      if (domNode.scrollIntoView)
        domNode.scrollIntoView();
      if (domNode.focus)
        domNode.focus();
    }, 3000);
  }

  public onMottagningChecked(item: Mottagning) {
    var index: number = -1;
    var i: number = 0;
    for (const mottagning of this.patient.mottagnings) {
      if (mottagning.id === item.id) {
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

  public checkPnrDuplicate() {
    if (this.patient.id)
      return;
    this.http.get('/api/patient/pnr/' + this.patient.pnr, this.patient.pnr).map(response => response.json()).subscribe(
      (p: Patient) => {
        if (p.id) {
          this.abortShowErrorAndFocus("Personnummer finns redan!", this.pnrInput);
        }
      }
    );
  }

}
