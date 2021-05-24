import { Component, OnInit } from "@angular/core";
import { SiteService } from "../../core/services/site.service";

@Component({
  selector: "app-sites-list",
  templateUrl: "./sites-list.component.html",
  styleUrls: ["./sites-list.component.css"],
})
export class SitesListComponent implements OnInit {
  sites: Array<any> = [];
  searchInput: string = "";

  constructor(private siteService: SiteService) {}

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
}
