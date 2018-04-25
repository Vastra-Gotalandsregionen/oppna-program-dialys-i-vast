import {Component, OnInit} from '@angular/core';
import {Mottagning} from "../../../model/Mottagning";
import {JwtHttp} from "../../../core/jwt-http";

@Component({
  selector: 'app-mottagnings-list',
  templateUrl: './mottagnings-list.component.html',
  styleUrls: ['./mottagnings-list.component.scss']
})
export class MottagningsListComponent implements OnInit {

  constructor(private http: JwtHttp) {
  }

  displayedColumns = ['id', 'name', 'personnelCount', 'patientCount'];

  mottagnings: MottagningExt[];

  ngOnInit() {
    console.log('MottagningsListComponent');
    this.fetchListItems();
  }

  fetchListItems() {
    this.http.get('/api/mottagning').map(response => response.json())
      .subscribe(
        (mottagnings: any) => {
          this.mottagnings = mottagnings;
          console.log("got data ", this.mottagnings);
          var url: string = '/api/generic/counts/Mottagning/users/iids/';
          var ids = '';
          this.mottagnings.forEach((i) => ids += 'i' + i.id);
          this.http.get(url + ids)
            .map(response => response.json())
            .subscribe((counts: Array<number>) => {
              for (var c = 0; c < counts.length; c++) this.mottagnings[c].personalCount = counts[c];
            });
          url = '/api/generic/counts/Mottagning/patients/iids/';
          this.http.get(url + ids)
            .map(response => response.json())
            .subscribe((counts: Array<number>) => {
              for (var c = 0; c < counts.length; c++) this.mottagnings[c].patientCount = counts[c];
            });
        }
      );
  }

}

class MottagningExt extends Mottagning {
  personalCount: number;
  patientCount: number;
}
