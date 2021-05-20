import { Component, OnInit } from "@angular/core";
import { faUser } from "@fortawesome/free-solid-svg-icons";
import { AuthService } from "../../core/services/auth.service";
import { Router } from "@angular/router";

@Component({
  selector: "navbar-menu",
  templateUrl: "./menu.component.html",
  styleUrls: ["./menu.component.css"],
})
export class MenuComponent implements OnInit {
  userIcon = faUser;

  constructor(public authService: AuthService, private router: Router) {}

  ngOnInit(): void {}

  isLoggedIn() {
    return this.authService.isLoggedIn();
  }

  logout() {
    this.authService.logout();
    this.router.navigate(["/login"]);
  }
}
