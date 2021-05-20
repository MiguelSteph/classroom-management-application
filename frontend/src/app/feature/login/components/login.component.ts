import { Component, OnInit } from "@angular/core";
import { AuthService } from "../../../core/services/auth.service";
import { Router } from "@angular/router";
import { AppError } from "../../../shared/exceptions/app-error";

@Component({
  selector: "login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"],
})
export class LoginComponent implements OnInit {
  invalidLogin: boolean = false;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.invalidLogin = false;
  }

  login(credentials) {
    this.authService.login(credentials).subscribe(
      (result) => {
        if (result) {
          this.router.navigate(["/"]);
        } else {
          this.invalidLogin = true;
        }
      },
      (error: AppError) => {
        this.invalidLogin = true;
      }
    );
  }
}
