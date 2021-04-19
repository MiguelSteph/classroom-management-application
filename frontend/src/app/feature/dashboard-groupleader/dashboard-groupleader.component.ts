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

  constructor() { }

  ngOnInit(): void {
    this.showCreateBookingRequestForm = false;
  }

  public showCreateRequestForm() {
    this.showCreateBookingRequestForm = true;
  }

  public hideCreateRequestForm() {
    this.showCreateBookingRequestForm = false;
  }

}
