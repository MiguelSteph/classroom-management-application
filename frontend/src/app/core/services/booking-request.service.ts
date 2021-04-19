import { Injectable } from '@angular/core';
import {CrudService} from "./crud.service";
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";

const BOOKING_REQUEST_RESOURCE_LINK = environment.resourceServerEndPoint + '/bookingRequests';

@Injectable({
  providedIn: 'root'
})
export class BookingRequestService extends CrudService {

  constructor(private httpClient: HttpClient) {
    super(httpClient, BOOKING_REQUEST_RESOURCE_LINK);
  }
}
