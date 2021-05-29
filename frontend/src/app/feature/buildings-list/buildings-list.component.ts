import { Component, OnInit } from "@angular/core";
import { SiteService } from "../../core/services/site.service";
import { BuildingService } from "../../core/services/building.service";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: "app-buildings-list",
  templateUrl: "./buildings-list.component.html",
  styleUrls: ["./buildings-list.component.css"],
})
export class BuildingsListComponent implements OnInit {
  site: string = "";
  sites: Array<any> = [];
  buildings: Array<any> = [];
  searchInput: string = "";

  constructor(
    private siteService: SiteService,
    private buildingService: BuildingService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.loadAllSites();
  }

  loadAllSites = () => {
    this.siteService.get().subscribe((data) => {
      this.sites = data as Object[];
      if (this.sites) {
        this.site = this.sites[0]["id"];
        this.loadBuildingsInCurrentSite();
      } else {
        this.site = "";
      }
    });
  };

  loadBuildingsInCurrentSite = () => {
    this.buildingService.getBuildingInSite(this.site).subscribe((data) => {
      this.buildings = data as Object[];
    });
  };

  filterBuildings = () => {
    if (this.searchInput.length === 0) {
      return this.buildings;
    }
    return this.buildings.filter((building) => {
      return (
        building.code.toUpperCase().indexOf(this.searchInput.toUpperCase()) >=
          0 ||
        building.name.toUpperCase().indexOf(this.searchInput.toUpperCase()) >= 0
      );
    });
  };

  showAddOrUpdateBuildingModal(content) {
    this.modalService
      .open(content, { ariaLabelledBy: "modal-basic-title" })
      .result.then((result) => {
        if (result === "BuildingAdded" || result === "BuildingUpdated") {
          this.loadBuildingsInCurrentSite();
        }
      });
  }
}
