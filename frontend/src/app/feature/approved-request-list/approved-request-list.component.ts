import { Component, OnInit } from "@angular/core";
import { BookingRequestService } from "../../core/services/booking-request.service";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";

export const APPROVED_STATUS = "approved";

@Component({
  selector: "approved-request-list",
  templateUrl: "./approved-request-list.component.html",
  styleUrls: ["./approved-request-list.component.css"],
})
export class ApprovedRequestListComponent implements OnInit {
  pageId: number = 1;
  totalPages: number;
  hasNextPage: boolean;
  hasPreviousPage: boolean;
  requestList: Array<any>;
  currentBookingRequest: any;

  constructor(
    private bookingRequestService: BookingRequestService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.loadDataFromServer(this.pageId);
  }

  loadDataFromServer(pageNumber: number) {
    this.bookingRequestService
      .getBookingRequestsPerStatus(pageNumber, APPROVED_STATUS)
      .subscribe((data) => {
        this.totalPages = data["totalPages"];
        this.hasNextPage = data["hasNextPage"];
        this.hasPreviousPage = data["hasPreviousPage"];
        this.requestList = data["data"];
        this.pageId = pageNumber;
      });
  }

  formatTime(timeStr: string) {
    return timeStr.split(":")[0] + "H";
  }

  showDetailsBookingRequest(content, currentItem) {
    this.currentBookingRequest = currentItem;
    this.modalService.open(content, { ariaLabelledBy: "modal-basic-title" });
  }
}
