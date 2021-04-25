import { Injectable } from '@angular/core';
import {CrudService} from "./crud.service";
import {environment} from "../../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {AuthService} from "./auth.service";
import {CustomUrlEncoderService} from "./custom-url-encoder.service";

const BOOKING_REQUEST_RESOURCE_LINK = environment.resourceServerEndPoint + '/bookingRequests';

@Injectable({
  providedIn: 'root'
})
export class BookingRequestService extends CrudService {

  constructor(private httpClient: HttpClient, private authService: AuthService) {
    super(httpClient, BOOKING_REQUEST_RESOURCE_LINK);
  }

  getSummary() {
    let customUrl = BOOKING_REQUEST_RESOURCE_LINK + '/summary';
    return this.get(customUrl);
  }

  getBookingRequestsPerStatus(pageId:number, status:string) {
    if (!pageId) {
      pageId = 1;
    }
    pageId--;
    let customUrl = BOOKING_REQUEST_RESOURCE_LINK + '/' + status;
    let params = new HttpParams({encoder: new CustomUrlEncoderService()});
    params = params.appendAll({
      username: this.authService.currentUser['user_name'],
      page: ""+pageId,
      size: ""+environment.pageSize
    });

    return this.get(customUrl, params);
  }

  cancelBookingRequest(requestId) {
    let customUrl = BOOKING_REQUEST_RESOURCE_LINK + '/' + requestId;
    return this.put(this.authService.currentUser['user_name'], customUrl);
  }

}
