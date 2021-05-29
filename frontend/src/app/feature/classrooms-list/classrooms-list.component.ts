import { Component, OnInit } from "@angular/core";
import { SiteService } from "../../core/services/site.service";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { ClassroomService } from "../../core/services/classroom.service";

@Component({
  selector: "app-classrooms-list",
  templateUrl: "./classrooms-list.component.html",
  styleUrls: ["./classrooms-list.component.css"],
})
export class ClassroomsListComponent implements OnInit {
  site: string = "";
  building: string = "";
  sites: Array<any> = [];
  buildings: Array<any> = [];
  classrooms: Array<any> = [];
  searchInput: string = "";

  constructor(
    private classroomService: ClassroomService,
    private siteService: SiteService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.loadAllSites();
  }

  loadAllSites = () => {
    const siteBackup = this.site;
    this.siteService.get().subscribe((data) => {
      this.sites = data as Object[];
      if (this.sites) {
        if (!siteBackup) {
          this.site = this.sites[0]["id"];
        } else {
          this.site = siteBackup;
        }
        this.loadBuildingsInCurrentSite();
        // this.loadClassroomsInCurrentBuilding();
      } else {
        this.site = "";
        this.building = "";
        this.buildings = [];
      }
    });
  };

  loadClassroomsInCurrentBuilding = () => {
    if (this.building === undefined || this.building === "") {
      this.classrooms = [];
    } else {
      let currentBuilding = this.buildings.filter((s) => s.id == this.building);
      if (currentBuilding && currentBuilding.length > 0) {
        this.classrooms = currentBuilding[0]["classrooms"];
      } else {
        this.classrooms = [];
      }
    }
  };

  loadBuildingsInCurrentSite = () => {
    if (this.site === undefined || this.site === "") {
      this.buildings = [];
      this.classrooms = [];
    } else {
      this.buildings = this.sites.filter((s) => s["id"] == this.site)[0][
        "buildings"
      ];
    }
    if (this.buildings && this.buildings.length > 0) {
      if (!this.building || !this.isCurrentBuildingValid()) {
        this.building = this.buildings[0]["id"];
      }
    } else {
      this.building = "";
    }
    this.loadClassroomsInCurrentBuilding();
  };

  private isCurrentBuildingValid() {
    const buildingResSearch = this.buildings.filter(
      (item) => item["id"] == this.building
    );
    return buildingResSearch && buildingResSearch.length > 0;
  }

  showAddOrUpdateClassroomModal(content) {
    this.modalService
      .open(content, { ariaLabelledBy: "modal-basic-title" })
      .result.then((result) => {
        if (result === "ClassroomAdded" || result === "ClassroomUpdated") {
          this.loadAllSites();
        }
      });
  }

  filterClassrooms = () => {
    if (this.searchInput.length === 0) {
      return this.classrooms;
    }
    return this.classrooms.filter((classroom) => {
      return (
        classroom.code.toUpperCase().indexOf(this.searchInput.toUpperCase()) >=
          0 ||
        classroom.name.toUpperCase().indexOf(this.searchInput.toUpperCase()) >=
          0
      );
    });
  };
}
