import { Component, OnInit } from '@angular/core';
import {faUser} from "@fortawesome/free-solid-svg-icons";
import {AuthService} from "../../core/services/auth.service";

@Component({
  selector: 'navbar-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  userIcon = faUser;

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
  }

  isLoggedIn() {
    return this.authService.isLoggedIn();
  }
}
