import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'booking-request-detail',
  templateUrl: './booking-request-detail.component.html',
  styleUrls: ['./booking-request-detail.component.css']
})
export class BookingRequestDetailComponent implements OnInit {

  @Input() currentBookingRequest;
  @Input() modalDialog;

  constructor() { }

  ngOnInit(): void {
  }

  formatTime(timeStr: string) {
    return timeStr.split(':')[0] + 'H';
  }
}
