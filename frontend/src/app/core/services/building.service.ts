import { Injectable } from "@angular/core";
import { environment } from "../../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { CrudService } from "./crud.service";

const BUILDING_RESOURCE_LINK =
  environment.resourceServerEndPoint + "/buildings";

@Injectable({
  providedIn: "root",
})
export class BuildingService extends CrudService {
  constructor(private httpClient: HttpClient) {
    super(httpClient, BUILDING_RESOURCE_LINK);
  }

  getBuildingInSite(siteId: string) {
    const customUrl = BUILDING_RESOURCE_LINK + "/site/" + siteId;
    return this.get(customUrl);
  }
}
