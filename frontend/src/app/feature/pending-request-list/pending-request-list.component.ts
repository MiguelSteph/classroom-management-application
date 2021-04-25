import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {BookingRequestService} from "../../core/services/booking-request.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {AuthService} from "../../core/services/auth.service";
import {ToastService} from "../../core/services/toast.service";

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
  currentBookingRequest: any;

  @Output() requestCancelled: EventEmitter<any> = new EventEmitter<any>();

  constructor(private bookingRequestService: BookingRequestService,
              private modalService: NgbModal,
              private toastService: ToastService) { }

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

  showCancelBookingRequest(content, currentItem) {
    this.currentBookingRequest = currentItem;
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'})
      .result.then(result => {
        if (result === 'Click Confirm') {
          this.cancelCurrentBookingRequest();
        }
    });
  }

  cancelCurrentBookingRequest() {
    this.bookingRequestService.cancelBookingRequest(this.currentBookingRequest['id'])
      .subscribe(result => {
        this.toastService.show('Booking request successfully cancelled.', {
          classname: 'bg-success text-light',
          delay: 3000 ,
          autohide: true
        });
        const index = this.requestList.indexOf(this.currentBookingRequest);
        this.requestList.splice(index, 1);
        this.requestCancelled.emit();
      });
  }

  formatTime(timeStr: string) {
    return timeStr.split(':')[0] + 'H';
  }
}
