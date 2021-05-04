import { Component, OnInit } from '@angular/core';
import {BookingRequestService} from "../../core/services/booking-request.service";
import {AuthService} from "../../core/services/auth.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

export const CANCELLED_STATUS = 'cancelled';

@Component({
  selector: 'cancelled-request-list',
  templateUrl: './cancelled-request-list.component.html',
  styleUrls: ['./cancelled-request-list.component.css']
})
export class CancelledRequestListComponent implements OnInit {

  pageId: number = 1;
  totalPages: number;
  hasNextPage: boolean;
  hasPreviousPage:boolean
  requestList: Array<any>;
  currentBookingRequest: any;

  constructor(private bookingRequestService: BookingRequestService,
              public authService: AuthService,
              private modalService: NgbModal) { }

  ngOnInit(): void {
    this.loadDataFromServer(this.pageId);
  }

  loadDataFromServer(pageNumber:number) {

    this.bookingRequestService.getBookingRequestsPerStatus(pageNumber, CANCELLED_STATUS)
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

  showDetailsBookingRequest(content, currentItem) {
    this.currentBookingRequest = currentItem;
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'});
  }
}
