import { Component, Input, OnInit } from "@angular/core";
import { ClassroomService } from "../../core/services/classroom.service";
import { ToastService } from "../../core/services/toast.service";

@Component({
  selector: "new-classroom",
  templateUrl: "./new-classroom.component.html",
  styleUrls: ["./new-classroom.component.css"],
})
export class NewClassroomComponent implements OnInit {
  @Input() operationType: string;
  @Input() sites: Object[];
  @Input() currentSiteId: string;
  @Input() currentBuildingId: string;
  @Input() currentClassroom: Object;
  @Input() modalDialog;

  buildings: Array<any> = [];
  siteId: string = "";
  buildingId: string = "";
  code: string = "";
  name: string = "";
  formError: string = "";
  enableOrDisableBtn: string = "";

  constructor(
    private classroomService: ClassroomService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.prefillFormData();
  }

  prefillFormData = () => {
    this.code = "";
    this.name = "";
    if (this.sites) {
      if (this.currentSiteId) {
        this.siteId = this.currentSiteId;
      } else {
        this.siteId = this.sites[0]["id"];
      }
      this.loadBuildingsInCurrentSite();

      if (this.buildings) {
        if (this.currentBuildingId) {
          this.buildingId = this.currentBuildingId;
        } else {
          this.buildingId = this.buildings[0]["id"];
        }
      }
    }
    if (this.currentClassroom) {
      this.code = this.currentClassroom["code"];
      this.name = this.currentClassroom["name"];
      // this.buildingId = this.currentClassroom["buildingId"];
      if (this.operationType === "ENABLE_DISABLE") {
        this.enableOrDisableBtn = this.currentClassroom["enabled"]
          ? "Disable"
          : "Enable";
      }
    }
  };

  updateClassroom = (values) => {
    const body = {
      id: this.currentClassroom["id"],
      code: values.code,
      name: values.name,
      buildingId: values.buildingId,
      enabled: this.currentClassroom["enabled"],
    };
    if (this.operationType === "ENABLE_DISABLE") {
      body.enabled = !this.currentClassroom["enabled"];
    }
    this.classroomService.put(body).subscribe(
      () => {
        this.toastService.show("Classroom successfully updated.", {
          classname: "bg-success text-light",
          delay: 3000,
          autohide: true,
        });

        this.closeModalDialog("ClassroomUpdated");
      },
      (error) => {
        this.formError = error.error.error.message;
      }
    );
  };

  addNewClassroom = (values) => {
    const body = {
      code: values.code,
      name: values.name,
      buildingId: values.buildingId,
      enabled: true,
    };
    this.classroomService.post(body).subscribe(
      () => {
        this.toastService.show("New Classroom successfully created.", {
          classname: "bg-success text-light",
          delay: 3000,
          autohide: true,
        });
        this.closeModalDialog("ClassroomAdded");
      },
      (error) => {
        this.formError = error.error.error.message;
      }
    );
  };

  loadBuildingsInCurrentSite = () => {
    if (this.siteId === undefined || this.siteId === "") {
      this.buildings = [];
    } else {
      this.buildings = this.sites.filter((s) => s["id"] == this.siteId)[0][
        "buildings"
      ];
    }
    if (this.buildings && this.buildings.length > 0) {
      this.buildingId = this.buildings[0]["id"];
    } else {
      this.buildingId = "";
    }
  };

  getDialogTitle = () => {
    let title = "";
    if (this.operationType === "ADD") {
      title = "ADD NEW CLASSROOM";
    } else if (this.operationType === "UPDATE") {
      title = "UPDATE CLASSROOM";
    } else if (this.operationType === "ENABLE_DISABLE") {
      title = "UPDATE CLASSROOM STATUS";
    }
    return title;
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
      return "Code is required. Please enter the classroom code";
    }
    return undefined;
  };

  getNameError = () => {
    if (this.code.length === 0) {
      return "Name is required. Please enter the classroom name";
    }
    return undefined;
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
