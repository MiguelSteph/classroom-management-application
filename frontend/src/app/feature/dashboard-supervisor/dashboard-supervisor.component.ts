import { Component, OnInit } from "@angular/core";
import { BookingRequestService } from "../../core/services/booking-request.service";
import { faFileAlt, faPlusSquare } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "dashboard-supervisor",
  templateUrl: "./dashboard-supervisor.component.html",
  styleUrls: ["./dashboard-supervisor.component.css"],
})
export class DashboardSupervisorComponent implements OnInit {
  pendingBookingRequestIcon = faFileAlt;

  currentTab: string = "pending";

  pendingCount: number;
  rejectedCount: number;
  cancelledCount: number;
  approvedCount: number;
  totalCount: number;

  constructor(private bookingRequestService: BookingRequestService) {}

  ngOnInit(): void {
    this.loadSummary();
  }

  loadSummary() {
    this.bookingRequestService.getSummary().subscribe((summary) => {
      this.pendingCount = summary["pendingCount"];
      this.rejectedCount = summary["rejectedCount"];
      this.cancelledCount = summary["cancelledCount"];
      this.approvedCount = summary["approvedCount"];
      this.totalCount = summary["totalCount"];
    });
  }

  getApprovedPercentage() {
    if (this.approvedCount === 0) {
      return 0;
    }

    return Math.round((this.approvedCount * 100) / this.totalCount);
  }

  getCancelledPercentage() {
    if (this.cancelledCount === 0) {
      return 0;
    }
    return Math.round((this.cancelledCount * 100) / this.totalCount);
  }

  getRejectedPercentage() {
    if (this.rejectedCount === 0) {
      return 0;
    }
    return Math.round((this.rejectedCount * 100) / this.totalCount);
  }

  getPendingPercentage() {
    if (this.pendingCount === 0) {
      return 0;
    }
    return Math.round((this.pendingCount * 100) / this.totalCount);
  }

  public isPending() {
    return this.currentTab === "pending";
  }

  public pendingClick() {
    this.currentTab = "pending";
  }

  public rejectedClick() {
    this.currentTab = "rejected";
  }

  public isRejected() {
    return this.currentTab === "rejected";
  }

  public cancelledClick() {
    this.currentTab = "cancelled";
  }

  public isCancelled() {
    return this.currentTab === "cancelled";
  }

  public approvedClick() {
    this.currentTab = "approved";
  }

  public isApproved() {
    return this.currentTab === "approved";
  }

  onRequestApproved() {
    this.pendingCount--;
    this.approvedCount++;
  }

  onRequestRejected() {
    this.pendingCount--;
    this.rejectedCount++;
  }
}
