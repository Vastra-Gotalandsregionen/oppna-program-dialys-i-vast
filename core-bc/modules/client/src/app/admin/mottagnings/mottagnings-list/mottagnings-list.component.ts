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

  displayedColumns = ['name'];

  mottagnings: Mottagning[];

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
        }
      );
  }

}
