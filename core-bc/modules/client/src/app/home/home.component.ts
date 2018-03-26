import {Component, Input, OnInit} from '@angular/core';
import {AuthService} from '../core/auth/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {


  @Input() filter: string;

  // @Input() userName: string;

  constructor(private authService: AuthService) {

  }

  ngOnInit() {
    //this.userName = this.authService.getLoggedInUserId();
    this.filter = '';
  }

  get loggedIn() {
    return this.authService.isAuthenticated();
  }

}
