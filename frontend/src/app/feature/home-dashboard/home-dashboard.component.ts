import { Component, OnInit } from "@angular/core";
import { AuthService } from "../../core/services/auth.service";
import { Router } from "@angular/router";

@Component({
  selector: "home-dashboard",
  templateUrl: "./home-dashboard.component.html",
  styleUrls: ["./home-dashboard.component.css"],
})
export class HomeDashboardComponent implements OnInit {
  constructor(private router: Router, private authService: AuthService) {}

  ngOnInit(): void {
    if (this.authService.hasGroupLeaderRole()) {
      this.router.navigate(["/groupleader"]);
    } else if (this.authService.hasSupervisorRole()) {
      this.router.navigate(["/supervisor/booking-requests"]);
    } else if (this.authService.hasAdminRole()) {
      this.router.navigate(["/admin/sites"]);
    }
  }
}
