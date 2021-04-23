import { Component, OnInit } from '@angular/core';
import {BookingRequestService} from "../../core/services/booking-request.service";

export const PENDING_STATUS = 'pending';

@Component({
  selector: 'pending-request-list',
  templateUrl: './pending-request-list.component.html',
  styleUrls: ['./pending-request-list.component.css']
})
export class PendingRequestListComponent implements OnInit {

  pageId: number = 1;
  totalPages: number;
  hasNextPage: boolean;
  hasPreviousPage:boolean
  requestList: Array<any>;

  constructor(private bookingRequestService: BookingRequestService) { }

  ngOnInit(): void {
    this.loadDataFromServer(this.pageId);
  }

  loadDataFromServer(pageNumber:number) {

    this.bookingRequestService.getBookingRequestsPerStatus(pageNumber, PENDING_STATUS)
      .subscribe( data => {
        this.totalPages = data['totalPages'];
        this.hasNextPage = data['hasNextPage'];
        this.hasPreviousPage = data['hasPreviousPage'];
        this.requestList = data['data'];
        this.pageId = pageNumber;
      });
  }

  formatTime(timeStr: string) {
    return timeStr.split(':')[0] + 'H';
  }
}
