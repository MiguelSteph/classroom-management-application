import { Injectable } from "@angular/core";
import { CrudService } from "./crud.service";
import { environment } from "../../../environments/environment";
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { AuthService } from "./auth.service";
import { CustomUrlEncoderService } from "./custom-url-encoder.service";

const BOOKING_REQUEST_RESOURCE_LINK =
  environment.resourceServerEndPoint + "/bookingRequests";

@Injectable({
  providedIn: "root",
})
export class BookingRequestService extends CrudService {
  constructor(
    private httpClient: HttpClient,
    private authService: AuthService
  ) {
    super(httpClient, BOOKING_REQUEST_RESOURCE_LINK);
  }

  getSummary() {
    let customUrl = BOOKING_REQUEST_RESOURCE_LINK + "/summary";
    let params: HttpParams = new HttpParams({
      encoder: new CustomUrlEncoderService(),
    });
    params = params.append(
      "username",
      this.authService.currentUser["user_name"]
    );
    return this.get(customUrl, params);
  }

  getBookingRequestsPerStatus(pageId: number, status: string) {
    if (!pageId) {
      pageId = 1;
    }
    pageId--;
    let customUrl = BOOKING_REQUEST_RESOURCE_LINK + "/" + status;
    let params = new HttpParams({ encoder: new CustomUrlEncoderService() });
    params = params.appendAll({
      username: this.authService.currentUser["user_name"],
      page: "" + pageId,
      size: "" + environment.pageSize,
    });

    return this.get(customUrl, params);
  }

  cancelBookingRequest(requestId) {
    let customUrl = BOOKING_REQUEST_RESOURCE_LINK + "/" + requestId + "/cancel";
    return this.put(this.authService.currentUser["user_name"], customUrl);
  }

  approveBookingRequest(requestId) {
    let customUrl =
      BOOKING_REQUEST_RESOURCE_LINK + "/" + requestId + "/approve";
    return this.put(this.authService.currentUser["user_name"], customUrl);
  }

  rejectBookingRequest(requestId, rejectionReason) {
    let customUrl = BOOKING_REQUEST_RESOURCE_LINK + "/" + requestId + "/reject";
    let requestBody = {
      username: this.authService.currentUser["user_name"],
      reason: rejectionReason,
    };

    return this.put(
      JSON.stringify(requestBody),
      customUrl,
      new HttpHeaders({ "Content-Type": "application/json" })
    );
  }
}
