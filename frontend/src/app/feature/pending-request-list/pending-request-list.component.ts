import { Component, EventEmitter, OnInit, Output } from "@angular/core";
import { BookingRequestService } from "../../core/services/booking-request.service";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { AuthService } from "../../core/services/auth.service";
import { ToastService } from "../../core/services/toast.service";

export const PENDING_STATUS = "pending";

@Component({
  selector: "pending-request-list",
  templateUrl: "./pending-request-list.component.html",
  styleUrls: ["./pending-request-list.component.css"],
})
export class PendingRequestListComponent implements OnInit {
  pageId: number = 1;
  totalPages: number;
  hasNextPage: boolean;
  hasPreviousPage: boolean;
  requestList: Array<any>;
  currentBookingRequest: any;

  isRejection: boolean;
  modalTitle: string;

  @Output() requestCancelled: EventEmitter<any> = new EventEmitter<any>();
  @Output() requestApproved: EventEmitter<any> = new EventEmitter<any>();
  @Output() requestRejected: EventEmitter<any> = new EventEmitter<any>();

  constructor(
    private bookingRequestService: BookingRequestService,
    private modalService: NgbModal,
    private toastService: ToastService,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadDataFromServer(this.pageId);
  }

  loadDataFromServer(pageNumber: number) {
    this.bookingRequestService
      .getBookingRequestsPerStatus(pageNumber, PENDING_STATUS)
      .subscribe((data) => {
        this.totalPages = data["totalPages"];
        this.hasNextPage = data["hasNextPage"];
        this.hasPreviousPage = data["hasPreviousPage"];
        this.requestList = data["data"];
        this.pageId = pageNumber;
      });
  }

  showDetailsBookingRequest(content, currentItem) {
    this.currentBookingRequest = currentItem;
    this.modalService.open(content, { ariaLabelledBy: "modal-basic-title" });
  }

  showRejectBookingRequest(content, currentItem) {
    this.modalTitle = "Reject Booking Request";
    this.isRejection = true;
    this.currentBookingRequest = currentItem;
    this.modalService
      .open(content, { ariaLabelledBy: "modal-basic-title" })
      .result.then((result) => {
        if (result[0] === "Click Confirm") {
          this.rejectCurrentBookingRequest(result[1]["reason"]);
        }
      });
  }

  rejectCurrentBookingRequest(reason) {
    this.bookingRequestService
      .rejectBookingRequest(this.currentBookingRequest["id"], reason)
      .subscribe((result) => {
        this.toastService.show("Booking request successfully rejected.", {
          classname: "bg-success text-light",
          delay: 3000,
          autohide: true,
        });
        const index = this.requestList.indexOf(this.currentBookingRequest);
        this.requestList.splice(index, 1);
        this.requestRejected.emit();
      });
  }

  showApproveBookingRequest(content, currentItem) {
    this.modalTitle = "Approve Booking Request";
    this.isRejection = false;
    this.currentBookingRequest = currentItem;
    this.modalService
      .open(content, { ariaLabelledBy: "modal-basic-title" })
      .result.then((result) => {
        if (result === "Click Confirm") {
          this.approveCurrentBookingRequest();
        }
      });
  }

  approveCurrentBookingRequest() {
    this.bookingRequestService
      .approveBookingRequest(this.currentBookingRequest["id"])
      .subscribe((result) => {
        this.toastService.show("Booking request successfully approved.", {
          classname: "bg-success text-light",
          delay: 3000,
          autohide: true,
        });
        const index = this.requestList.indexOf(this.currentBookingRequest);
        this.requestList.splice(index, 1);
        this.requestApproved.emit();
      });
  }

  showCancelBookingRequest(content, currentItem) {
    this.isRejection = false;
    this.modalTitle = "Cancel Booking Request";
    this.currentBookingRequest = currentItem;
    this.modalService
      .open(content, { ariaLabelledBy: "modal-basic-title" })
      .result.then((result) => {
        if (result === "Click Confirm") {
          this.cancelCurrentBookingRequest();
        }
      });
  }

  cancelCurrentBookingRequest() {
    this.bookingRequestService
      .cancelBookingRequest(this.currentBookingRequest["id"])
      .subscribe((result) => {
        this.toastService.show("Booking request successfully cancelled.", {
          classname: "bg-success text-light",
          delay: 3000,
          autohide: true,
        });
        const index = this.requestList.indexOf(this.currentBookingRequest);
        this.requestList.splice(index, 1);
        this.requestCancelled.emit();
      });
  }

  formatTime(timeStr: string) {
    return timeStr.split(":")[0] + "H";
  }
}
