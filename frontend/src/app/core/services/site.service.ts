import { Injectable } from "@angular/core";
import { CrudService } from "./crud.service";
import { HttpClient } from "@angular/common/http";
import { environment } from "../../../environments/environment";

const SITE_RESOURCE_LINK = environment.resourceServerEndPoint + "/sites";

@Injectable({
  providedIn: "root",
})
export class SiteService extends CrudService {
  constructor(private httpClient: HttpClient) {
    super(httpClient, SITE_RESOURCE_LINK);
  }
}
