import { Component, Input, OnInit } from "@angular/core";
import { SiteService } from "../../core/services/site.service";
import { ToastService } from "../../core/services/toast.service";

@Component({
  selector: "new-site",
  templateUrl: "./new-site.component.html",
  styleUrls: ["./new-site.component.css"],
})
export class NewSiteComponent implements OnInit {
  @Input() operationType: string;
  @Input() currentSite: Object;
  @Input() modalDialog;

  code: string = "";
  name: string = "";
  formError: string = "";
  enableOrDisableBtn: string = "";

  constructor(
    private siteService: SiteService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.prefillFormData();
    this.enableOrDisableBtn = this.currentSite["enabled"]
      ? "Disable"
      : "Enable";
  }

  updateSite = (values) => {
    const body = {
      id: this.currentSite["id"],
      code: values.code,
      name: values.name,
      enabled: this.currentSite["enabled"],
    };
    if (this.operationType === "ENABLE_DISABLE") {
      body.enabled = !this.currentSite["enabled"];
    }
    this.siteService.put(body).subscribe(
      () => {
        this.toastService.show("Site successfully updated.", {
          classname: "bg-success text-light",
          delay: 3000,
          autohide: true,
        });
        this.closeModalDialog("SiteUpdated");
      },
      (error) => {
        this.formError = error.error.error.message;
      }
    );
  };

  addNewSite = (values) => {
    this.siteService.post(values).subscribe(
      () => {
        this.toastService.show("New Site successfully created.", {
          classname: "bg-success text-light",
          delay: 3000,
          autohide: true,
        });
        this.closeModalDialog("SiteAdded");
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

  getCodeError = () => {
    if (this.code.length === 0) {
      return "Code is required. Please enter the site code";
    }
    return undefined;
  };

  getNameError = () => {
    if (this.code.length === 0) {
      return "Name is required. Please enter the site name";
    }
    return undefined;
  };

  prefillFormData = () => {
    this.code = "";
    this.name = "";
    if (this.currentSite) {
      this.code = this.currentSite["code"];
      this.name = this.currentSite["name"];
    }
  };

  getDialogTitle = () => {
    let title = "";
    if (this.operationType === "ADD") {
      title = "ADD NEW SITE";
    } else if (this.operationType === "UPDATE") {
      title = "UPDATE SITE";
    } else if (this.operationType === "ENABLE_DISABLE") {
      title = "UPDATE SITE STATUS";
    }
    return title;
  };

  isAdd = () => {
    return this.operationType === "ADD";
  };

  isUpdate = () => {
    return this.operationType === "UPDATE";
  };

  isEnableOrDisable = () => {
    return this.operationType === "ENABLE_DISABLE";
  };
}
