import { Component, OnInit } from "@angular/core";
import { SiteService } from "../../core/services/site.service";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: "app-sites-list",
  templateUrl: "./sites-list.component.html",
  styleUrls: ["./sites-list.component.css"],
})
export class SitesListComponent implements OnInit {
  sites: Array<any> = [];
  searchInput: string = "";

  constructor(
    private siteService: SiteService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.loadSites();
  }

  loadSites = () => {
    this.siteService.get().subscribe((data) => {
      this.sites = data as Object[];
    });
  };

  filterSites = () => {
    if (this.searchInput.length === 0) {
      return this.sites;
    }
    return this.sites.filter((site) => {
      return (
        site.code.toUpperCase().indexOf(this.searchInput.toUpperCase()) >= 0 ||
        site.name.toUpperCase().indexOf(this.searchInput.toUpperCase()) >= 0
      );
    });
  };

  showAddOrUpdateSiteModal(content) {
    this.modalService
      .open(content, { ariaLabelledBy: "modal-basic-title" })
      .result.then((result) => {
        if (result === "SiteAdded" || result === "SiteUpdated") {
          this.loadSites();
        }
      });
  }
}
