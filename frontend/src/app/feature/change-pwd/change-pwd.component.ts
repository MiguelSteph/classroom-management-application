import { Component, OnInit } from "@angular/core";
import { UserService } from "../../core/services/user.service";
import { ToastService } from "../../core/services/toast.service";
import { ActivatedRoute, Router } from "@angular/router";
import { AuthService } from "../../core/services/auth.service";

@Component({
  selector: "app-change-pwd",
  templateUrl: "./change-pwd.component.html",
  styleUrls: ["./change-pwd.component.css"],
})
export class ChangePwdComponent implements OnInit {
  oldPassword: string = "";
  newPassword: string = "";
  verifyPassword: string = "";
  formError: string = "";
  defaultPwdIssue: boolean = false;

  constructor(
    private userService: UserService,
    private toastService: ToastService,
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.defaultPwdIssue = false;
    this.route.queryParamMap.subscribe((queryParams) => {
      const defaultPwd = queryParams.get("defaultPwdIssue");
      if (defaultPwd === "yes") {
        this.defaultPwdIssue = true;
      }
    });
  }

  updatePassword(values) {
    this.userService
      .updatePwd(values.oldPassword, values.newPassword)
      .subscribe(
        () => {
          this.toastService.show("Password successfully updated.", {
            classname: "bg-success text-light",
            delay: 3000,
            autohide: true,
          });
          this.authService.logout();
          this.router.navigate(["/login"]);
        },
        (error) => {
          console.log(error);
          this.formError = error.error.error.message;
        }
      );
  }

  dismissGlobalError = () => {
    this.formError = "";
  };

  getGlobalError = () => {
    return this.formError;
  };
}
