import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Patient} from '../../model/Patient';
import {AuthService} from '../../core/auth/auth.service';
import {Observable} from 'rxjs/Observable';
import {ApkFormComponent} from "../apk-form/apk-form.component";
import {JwtHttp} from "../../core/jwt-http";
import {Flik} from "../../model/Flik";

@Component({
  selector: 'app-apk-detail',
  templateUrl: './patient-add-requisition.component.html',
  styleUrls: ['./patient-add-requisition.component.css']
})
export class PatientAddRequisitionComponent implements OnInit {

  @ViewChild(ApkFormComponent) apkFormComponent: ApkFormComponent;

  id: string;
  data: Patient;
  displayedColumns = ['namn', 'storlek', 'artNr'];

  private fliks: Array<Flik>;

  constructor(protected route: ActivatedRoute,
              protected http: JwtHttp,
              protected authService: AuthService) {
  }

  ngOnInit() {


    this.route.params.subscribe(params => {
      this.id = params.id;

      if (this.id) {
        const $data = this.http.get('/api/patient/' + this.id)
          .map(response => response.json())
          .share();
        $data.subscribe((data: Patient) => {
          this.data = data;

          const $fliks = this.http.get('/api/flik')
            .map(response => response.json())
            .share();
          $fliks.subscribe((data: Array<Flik>) => {
            this.fliks = data;
          });

        });
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
