import { Component, Input, OnInit } from "@angular/core";
import { BuildingService } from "../../core/services/building.service";
import { ToastService } from "../../core/services/toast.service";

@Component({
  selector: "new-building",
  templateUrl: "./new-building.component.html",
  styleUrls: ["./new-building.component.css"],
})
export class NewBuildingComponent implements OnInit {
  @Input() operationType: string;
  @Input() sites: Object[];
  @Input() currentBuilding: Object;
  @Input() modalDialog;

  siteId: string = "";
  code: string = "";
  name: string = "";
  formError: string = "";
  enableOrDisableBtn: string = "";

  constructor(
    private buildingService: BuildingService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.prefillFormData();
  }

  prefillFormData = () => {
    this.code = "";
    this.name = "";
    if (this.sites) {
      this.siteId = this.sites[0]["id"];
    }
    if (this.currentBuilding) {
      this.code = this.currentBuilding["code"];
      this.name = this.currentBuilding["name"];
      this.siteId = this.currentBuilding["siteId"];
      if (this.operationType === "ENABLE_DISABLE") {
        this.enableOrDisableBtn = this.currentBuilding["enabled"]
          ? "Disable"
          : "Enable";
      }
    }
  };

  updateBuilding = (values) => {
    const body = {
      id: this.currentBuilding["id"],
      code: values.code,
      name: values.name,
      siteId: values.siteId,
      enabled: this.currentBuilding["enabled"],
    };
    if (this.operationType === "ENABLE_DISABLE") {
      body.enabled = !this.currentBuilding["enabled"];
    }
    this.buildingService.put(body).subscribe(
      () => {
        this.toastService.show("Building successfully updated.", {
          classname: "bg-success text-light",
          delay: 3000,
          autohide: true,
        });
        this.closeModalDialog("BuildingUpdated");
      },
      (error) => {
        this.formError = error.error.error.message;
      }
    );
  };

  addNewBuilding = (values) => {
    this.buildingService.post(values).subscribe(
      () => {
        this.toastService.show("New Building successfully created.", {
          classname: "bg-success text-light",
          delay: 3000,
          autohide: true,
        });
        this.closeModalDialog("BuildingAdded");
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
      return "Code is required. Please enter the building code";
    }
    return undefined;
  };

  getNameError = () => {
    if (this.code.length === 0) {
      return "Name is required. Please enter the building name";
    }
    return undefined;
  };

  getDialogTitle = () => {
    let title = "";
    if (this.operationType === "ADD") {
      title = "ADD NEW BUILDING";
    } else if (this.operationType === "UPDATE") {
      title = "UPDATE BUILDING";
    } else if (this.operationType === "ENABLE_DISABLE") {
      title = "UPDATE BUILDING STATUS";
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
