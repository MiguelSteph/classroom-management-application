import { Component, Input, OnInit } from "@angular/core";
import { ToastService } from "../../core/services/toast.service";
import { UserService } from "../../core/services/user.service";
import { Observable } from "rxjs";

@Component({
  selector: "new-user",
  templateUrl: "./new-user.component.html",
  styleUrls: ["./new-user.component.css"],
})
export class NewUserComponent implements OnInit {
  @Input() operationType: string;
  @Input() roles: Object[];
  @Input() currentUser: Object;
  @Input() modalDialog;

  role: string = "";
  firstName: string = "";
  lastName: string = "";
  email: string = "";
  password: string = "";
  verifyPassword: string = "";
  formError: string = "";
  enableOrDisableBtn: string = "";

  constructor(
    private userService: UserService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.prefillFormData();
  }

  prefillFormData = () => {
    this.firstName = "";
    this.lastName = "";
    this.email = "";
    this.role = "";
    this.password = "";
    this.verifyPassword = "";
    if (this.roles) {
      this.role = this.roles[0]["role"] as string;
    }
    if (this.currentUser) {
      this.firstName = this.currentUser["firstName"];
      this.lastName = this.currentUser["lastName"];
      this.email = this.currentUser["email"];
      this.role = this.currentUser["role"];
      if (this.operationType === "ENABLE_DISABLE") {
        this.enableOrDisableBtn = this.currentUser["enabled"]
          ? "Disable"
          : "Enable";
      }
    }
  };

  enableOrDisableUser = () => {
    let updateUserObser: Observable<Object>;
    if (this.currentUser["enabled"]) {
      updateUserObser = this.userService.disableUser(this.currentUser["id"]);
    } else {
      updateUserObser = this.userService.enableUser(this.currentUser["id"]);
    }
    updateUserObser.subscribe(
      () => {
        this.toastService.show("User successfully enabled.", {
          classname: "bg-success text-light",
          delay: 3000,
          autohide: true,
        });
        this.closeModalDialog("UserUpdated");
      },
      (error) => {
        this.formError = error.error.error.message;
      }
    );
  };

  addNewUser = (values) => {
    delete values.verifyPassword;
    this.userService.post(values).subscribe(
      () => {
        this.toastService.show("New User successfully created.", {
          classname: "bg-success text-light",
          delay: 3000,
          autohide: true,
        });
        this.closeModalDialog("UserAdded");
      },
      (error) => {
        this.formError = error.error.error.message;
      }
    );
  };

  closeModalDialog = (closedData) => {
    this.modalDialog.close(closedData);
  };

  dismissGlobalError = () => {
    this.formError = "";
  };

  getGlobalError = () => {
    return this.formError;
  };

  getLastNameError = () => {
    if (this.lastName.length <= 2) {
      return "Last name should be at least two characters.";
    }
    return undefined;
  };

  getFirstNameError = () => {
    if (this.firstName.length <= 2) {
      return "First name should be at least two characters.";
    }
    return undefined;
  };

  getDialogTitle = () => {
    let title = "";
    if (this.operationType === "ADD") {
      title = "ADD NEW USER";
    } else if (this.operationType === "ENABLE_DISABLE") {
      title = "UPDATE USER STATUS";
    }
    return title;
  };

  isAdd = () => {
    return this.operationType === "ADD";
  };

  isEnableOrDisable = () => {
    return this.operationType === "ENABLE_DISABLE";
  };
}
