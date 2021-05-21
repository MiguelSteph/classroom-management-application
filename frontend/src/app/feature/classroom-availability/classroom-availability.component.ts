import { Component, OnInit } from "@angular/core";
import { ClassroomService } from "../../core/services/classroom.service";
import { SiteService } from "../../core/services/site.service";

@Component({
  selector: "classroom-availability",
  templateUrl: "./classroom-availability.component.html",
  styleUrls: ["./classroom-availability.component.css"],
})
export class ClassroomAvailabilityComponent implements OnInit {
  site = "";
  building = "";
  classroom = "";

  sitesInfo: any;
  buildingList = [];
  classroomList = [];
  currentAvailabilities = [];

  showNewAvailabilityForm: boolean;

  constructor(
    private classroomService: ClassroomService,
    private siteService: SiteService
  ) {}

  ngOnInit(): void {
    this.showNewAvailabilityForm = false;
    this.siteService.get().subscribe((data) => {
      this.sitesInfo = data as Object[];
      if (this.sitesInfo) {
        this.site = this.sitesInfo[0]["id"];
        this.updateBuildings();
      } else {
        this.site = "";
      }
    });
  }

  handleFormClosed() {
    this.showAddNewAvailabilityForm();
    this.loadCurrentAvailabilities();
  }

  showAddNewAvailabilityForm() {
    this.showNewAvailabilityForm = !this.showNewAvailabilityForm;
  }

  shrinkAvailabilities(currentClassroomId, fromDate, endDate) {
    this.classroomService
      .shrinkClassroomAvailabilities(currentClassroomId, fromDate, endDate)
      .subscribe(() => this.loadCurrentAvailabilities());
  }

  updateBuildings() {
    if (this.site === undefined || this.site === "") {
      this.buildingList = [];
      this.classroomList = [];
    } else {
      this.buildingList = this.sitesInfo.filter((s) => s["id"] == this.site)[0][
        "buildings"
      ];
    }
    if (this.buildingList && this.buildingList.length > 0) {
      this.building = this.buildingList[0]["id"];
    } else {
      this.building = "";
    }

    this.updateClassrooms();
  }

  public updateClassrooms() {
    if (this.building === undefined || this.building === "") {
      this.classroomList = [];
      this.currentAvailabilities = [];
    } else {
      let currentBuilding = this.buildingList.filter(
        (s) => s.id == this.building
      );
      if (currentBuilding) {
        this.classroomList = currentBuilding[0]["classrooms"];
      }
    }
    if (this.classroomList && this.classroomList.length > 0) {
      this.classroom = this.classroomList[0]["id"];
    } else {
      this.classroom = "";
    }
    this.loadCurrentAvailabilities();
    return this.classroomList;
  }

  loadCurrentAvailabilities() {
    if (this.canLoadAvailabilities()) {
      this.classroomService
        .getClassroomAllCurrentAvailabilities(this.classroom)
        .subscribe((data) => {
          this.currentAvailabilities = data as Array<any>;
        });
    } else {
      this.currentAvailabilities = [];
    }
  }

  formatTime(timeStr: string) {
    return timeStr.split(":")[0] + "H";
  }

  public canLoadAvailabilities() {
    return !(
      !this.site ||
      this.site === "" ||
      !this.building ||
      this.building === "" ||
      !this.classroom ||
      this.classroom === ""
    );
  }
}
