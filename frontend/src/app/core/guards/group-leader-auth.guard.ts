import { Injectable } from "@angular/core";
import { CanActivate, Router } from "@angular/router";
import { AuthService } from "../services/auth.service";

@Injectable({
  providedIn: "root",
})
export class GroupLeaderAuthGuard implements CanActivate {
  constructor(private router: Router, private authService: AuthService) {}

  canActivate() {
    if (this.authService.isLoggedIn() && this.authService.hasGroupLeaderRole())
      return true;

    this.router.navigate(["/login"]);
    return false;
  }
}
