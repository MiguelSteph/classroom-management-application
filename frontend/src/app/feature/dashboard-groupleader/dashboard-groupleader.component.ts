import { Component, OnInit } from '@angular/core';
import {faFileAlt, faPlusSquare} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'dashboard-groupleader',
  templateUrl: './dashboard-groupleader.component.html',
  styleUrls: ['./dashboard-groupleader.component.css']
})
export class DashboardGroupleaderComponent implements OnInit {

  showCreateBookingRequestForm = false;
  addBookingRequestIcon = faPlusSquare;
  pendingBookingRequestIcon = faFileAlt;

  currentTab: string = 'pending';

  constructor() { }

  ngOnInit(): void {
    this.showCreateBookingRequestForm = false;
  }

  public isPending() {
    return this.currentTab === 'pending';
  }

  public pendingClick() {
    this.currentTab = 'pending';
  }

  public rejectedClick() {
    this.currentTab = 'rejected';
  }

  public isRejected() {
    return this.currentTab === 'rejected';
  }

  public cancelledClick() {
    this.currentTab = 'cancelled';
  }

  public isCancelled() {
    return this.currentTab === 'cancelled';
  }

  public approvedClick() {
    this.currentTab = 'approved';
  }

  public isApproved() {
    return this.currentTab === 'approved';
  }

  public showCreateRequestForm() {
    this.showCreateBookingRequestForm = true;
  }

  public hideCreateRequestForm() {
    this.showCreateBookingRequestForm = false;
  }

}
