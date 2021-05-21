import { Component, Input, OnInit, Output, EventEmitter } from "@angular/core";
import { NgbDate } from "@ng-bootstrap/ng-bootstrap";
import { faCalendarAlt } from "@fortawesome/free-solid-svg-icons";
import { ClassroomService } from "../../core/services/classroom.service";
import { formatDate } from "@angular/common";
import { AuthService } from "../../core/services/auth.service";
import { ToastService } from "../../core/services/toast.service";

const SUNDAY = 0;
const MONDAY = 1;
const TUESDAY = 2;
const WEDNESDAY = 3;
const THURSDAY = 4;
const FRIDAY = 5;
const SATURDAY = 6;

@Component({
  selector: "new-classroom-availability",
  templateUrl: "./new-classroom-availability.component.html",
  styleUrls: ["./new-classroom-availability.component.css"],
})
export class NewClassroomAvailabilityComponent implements OnInit {
  @Input() classroomId: string;
  @Output() closeFormEvent = new EventEmitter<any>();

  readonly WEEK_DAYS = [
    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
  ];

  calendarAlt = faCalendarAlt;
  currentDate: Date = new Date();

  availFromDate: NgbDate = new NgbDate(
    this.currentDate.getFullYear(),
    this.currentDate.getMonth() + 1,
    this.currentDate.getDate()
  );

  availToDate: NgbDate = new NgbDate(
    this.currentDate.getFullYear(),
    this.currentDate.getMonth() + 1,
    this.currentDate.getDate()
  );

  has: boolean[] = [];
  timeRanges: [number, number][][] = [];
  fromTime: string[] = [];
  toTime: string[] = [];
  times: number[] = [];
  timeRangeOverlap: boolean[] = [];
  disableAll: boolean[] = [];
  invalidDateRange: boolean = false;

  constructor(
    private classroomService: ClassroomService,
    private authService: AuthService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.globalReset();
  }

  createClassroomAvailability(values) {
    let body = {};
    body["classroomId"] = this.classroomId;

    const inputFromDate = new Date(
      this.availFromDate.year,
      this.availFromDate.month - 1,
      this.availFromDate.day
    );

    const inputToDate = new Date(
      this.availToDate.year,
      this.availToDate.month - 1,
      this.availToDate.day
    );

    body["fromDate"] = formatDate(inputFromDate, "yyyy-MM-dd", "en-US");
    body["toDate"] = formatDate(inputToDate, "yyyy-MM-dd", "en-US");

    let timeRanges = {};
    this.WEEK_DAYS.forEach((day) => {
      if (this.has[day]) {
        const dayRanges = [];
        const compressedRanges = this.compressTimeRanges(this.timeRanges[day]);
        compressedRanges.forEach((range) => {
          dayRanges.push({
            start: range[0],
            end: range[1],
          });
        });
        if (dayRanges.length > 0) {
          timeRanges[this.getDayName(day)] = dayRanges;
        }
      }
    });
    body["timeRanges"] = timeRanges;
    body["supervisorUsername"] = this.authService.currentUser["user_name"];
    console.log(body);
    this.classroomService
      .createClassroomAvailability(this.classroomId, body)
      .subscribe(() => {
        this.toastService.show(
          "New Classroom Availabilities successfully created.",
          {
            classname: "bg-success text-light",
            delay: 3000,
            autohide: true,
          }
        );
        this.closeForm();
      });
  }

  compressTimeRanges(ranges: [number, number][]): [number, number][] {
    ranges = ranges.sort((a, b) => a[0] - b[0]);
    let compressedRanges = [];
    ranges.forEach((item) => {
      if (compressedRanges.length == 0) {
        compressedRanges.push(item);
      } else {
        if (item[0] == compressedRanges[compressedRanges.length - 1][1]) {
          const lastCompressedItem = compressedRanges.pop();
          compressedRanges.push([lastCompressedItem[0], item[1]]);
        } else {
          compressedRanges.push(item);
        }
      }
    });
    return compressedRanges;
  }

  closeForm() {
    this.closeFormEvent.emit();
  }

  canSubmit() {
    let result = false;
    this.WEEK_DAYS.forEach((day) => {
      result = result || (this.has[day] && this.timeRanges[day].length > 0);
    });
    return result;
  }

  globalReset() {
    this.invalidDateRange = false;
    this.initialiseTimesValues();
    this.initialiseDayCheckers();
    this.initialiseTimesRanges();
    this.initialiseTimeRangeOverLap();
    this.initialiseDisableAll();
  }

  initialiseDisableAll() {
    this.WEEK_DAYS.forEach((day) => {
      this.disableAll[day] = false;
    });
  }

  initialiseTimeRangeOverLap() {
    this.WEEK_DAYS.forEach((day) => {
      this.timeRangeOverlap[day] = false;
    });
  }

  initialiseTimesValues() {
    this.times = [];
    for (let i = 7; i <= 22; i++) {
      this.times.push(i);
    }

    this.WEEK_DAYS.forEach((day) => {
      this.fromTime[day] = "" + this.times[0];
    });

    this.WEEK_DAYS.forEach((day) => {
      this.toTime[day] = "" + this.times[1];
    });
  }

  initialiseTimeRangesBasedOnCheckers() {
    this.WEEK_DAYS.forEach((day) => {
      if (!this.has[day]) {
        this.timeRanges[day] = [];
      }
    });
  }

  initialiseDayCheckers() {
    this.WEEK_DAYS.forEach((day) => {
      this.has[day] = false;
    });
  }

  initialiseTimesRanges() {
    this.WEEK_DAYS.forEach((day) => {
      this.timeRanges[day] = [];
    });
  }

  removeFromTimeRanges(day, itemToRemove) {
    const indexOf = this.timeRanges[day].indexOf(itemToRemove);
    this.timeRanges[day].splice(indexOf, 1);
    this.resetAfterAddOrRemove(day);
  }

  resetAfterAddOrRemove(day) {
    this.timeRangeOverlap[day] = false;
    const firstValid = this.searchFirstValidInterval(this.timeRanges[day]);
    if (firstValid !== -1) {
      this.disableAll[day] = false;
      this.fromTime[day] = "" + firstValid;
      this.toTime[day] = "" + (firstValid + 1);
    } else {
      this.disableAll[day] = true;
    }
  }

  addTimeRange(day) {
    this.timeRanges[day].unshift([
      parseInt(this.fromTime[day]),
      parseInt(this.toTime[day]),
    ]);
    this.resetAfterAddOrRemove(day);
  }

  canAddTimeRange(day) {
    if (parseInt(this.fromTime[day]) >= parseInt(this.toTime[day])) {
      this.timeRangeOverlap[day] = true;
    } else {
      this.timeRangeOverlap[day] = false;
      this.timeRanges[day].forEach((timeRange) => {
        if (
          !(
            timeRange[0] >= parseInt(this.toTime[day]) ||
            timeRange[1] <= parseInt(this.fromTime[day])
          )
        ) {
          this.timeRangeOverlap[day] = true;
        }
      });
    }
  }

  searchFirstValidInterval(timeRanges) {
    for (let i of this.times) {
      if (
        this.checkIsValidInterval(timeRanges, i) &&
        i !== this.times[this.times.length - 1]
      ) {
        return i;
      }
    }
    return -1;
  }

  checkIsValidInterval(timeRanges, start) {
    let result = true;
    timeRanges.forEach((timeRange) => {
      if (
        !(
          timeRange[0] >= parseInt(start + 1) || timeRange[1] <= parseInt(start)
        )
      ) {
        result = false;
      }
    });
    return result;
  }

  checkAvailableDays() {
    this.invalidDateRange = false;
    const inputFromDate = new Date(
      this.availFromDate.year,
      this.availFromDate.month - 1,
      this.availFromDate.day
    );

    const inputToDate = new Date(
      this.availToDate.year,
      this.availToDate.month - 1,
      this.availToDate.day
    );

    this.classroomService
      .checkIfDateRangeIsValid(this.classroomId, inputFromDate, inputToDate)
      .subscribe((data) => {
        if (data === false) {
          this.invalidDateRange = true;
        } else {
          this.initialiseDayCheckers();

          let remaining = 7;
          for (
            let dateItem = inputFromDate;
            dateItem <= inputToDate;
            dateItem.setDate(dateItem.getDate() + 1)
          ) {
            this.has[dateItem.getDay()] = true;
            remaining = remaining - 1;
            if (remaining == 0) {
              break;
            }
          }
        }
      });
  }

  checkValidDateRange() {
    const inputFromDate = new Date(
      this.availFromDate.year,
      this.availFromDate.month - 1,
      this.availFromDate.day
    );

    const inputToDate = new Date(
      this.availToDate.year,
      this.availToDate.month - 1,
      this.availToDate.day
    );

    return inputFromDate <= inputToDate;
  }

  dateAvail = (date: NgbDate) => {
    let today = new Date(
      this.currentDate.getFullYear(),
      this.currentDate.getMonth() + 1,
      this.currentDate.getDate()
    );
    let inputDate = new Date(date.year, date.month, date.day);
    return inputDate < today;
  };

  getDayName(day) {
    let name = "";
    switch (day) {
      case 0:
        name = "Sunday";
        break;
      case 1:
        name = "Monday";
        break;
      case 2:
        name = "Tuesday";
        break;
      case 3:
        name = "Wednesday";
        break;
      case 4:
        name = "Thursday";
        break;
      case 5:
        name = "Friday";
        break;
      case 6:
        name = "Saturday";
        break;
      default:
        name = "";
    }
    return name;
  }
}
