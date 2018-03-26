import {Component, Input, OnInit} from '@angular/core';
import {AuthService} from '../core/auth/auth.service';
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  @Input() filter: string;

  constructor(private authService: AuthService, private router: Router) {

  }

  ngOnInit() {
    //this.userName = this.authService.getLoggedInUserId();
    this.filter = '';
  }

  get loggedIn() {
    return this.authService.isAuthenticated();
  }

  public quickSearchClicked(f) {
    this.router.navigate(['patienter'], {queryParams: {'filter': this.filter}});
  }
}
