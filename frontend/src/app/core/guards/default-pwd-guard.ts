import { Injectable } from "@angular/core";
import { CanActivate, Router } from "@angular/router";
import { AuthService } from "../services/auth.service";

@Injectable({
  providedIn: "root",
})
export class DefaultPwdGuard implements CanActivate {
  constructor(private router: Router, private authService: AuthService) {}

  canActivate() {
    if (this.authService.isLoggedIn() && !this.authService.isDefaultPwd())
      return true;

    this.router.navigate(["/change-password"], {
      queryParams: { defaultPwdIssue: "yes" },
    });
    return false;
  }
}
