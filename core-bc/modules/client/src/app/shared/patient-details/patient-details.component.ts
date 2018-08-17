import {Component, OnInit, Input} from '@angular/core';
import {Patient} from '../../model/Patient';
import {Observable} from 'rxjs/Observable';
import {JwtHttp} from "../../core/jwt-http";

@Component({
  selector: 'app-patient-details',
  templateUrl: './patient-details.component.html',
  styleUrls: ['./patient-details.component.scss']
})
export class PatientDetailsComponent implements OnInit {

  @Input('patientId') patientId: string;

  patient: Patient;
  showTempAddress: boolean = false;

  constructor(protected http: JwtHttp) {
  }

  ngOnInit() {

      if (this.patientId) {
        const $data = this.http.get('/api/patient/' + this.patientId)
          .map(response => response.json())
          .share();

        $data.subscribe((data: Patient) => {
          this.patient = data;
          this.showTempAddress = data.tempAdressFrom && new Date(data.tempAdressFrom).getTime() < Date.now();
          if (data.tempAdressTom && new Date(data.tempAdressTom).getTime() < Date.now()) {
            this.showTempAddress = false;
          }
        });
      }
  }


}
