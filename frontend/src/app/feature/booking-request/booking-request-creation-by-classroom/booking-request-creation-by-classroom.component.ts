import { Component, OnInit, Output, EventEmitter } from "@angular/core";
import { faCalendarAlt } from "@fortawesome/free-solid-svg-icons";
import { SiteService } from "../../../core/services/site.service";
import { NgbDate } from "@ng-bootstrap/ng-bootstrap";
import { ClassroomService } from "../../../core/services/classroom.service";
import { BookingRequestService } from "../../../core/services/booking-request.service";
import { AuthService } from "../../../core/services/auth.service";
import { formatDate } from "@angular/common";
import { ToastService } from "../../../core/services/toast.service";

@Component({
  selector: "booking-request-creation-by-classroom",
  templateUrl: "./booking-request-creation-by-classroom.component.html",
  styleUrls: ["./booking-request-creation-by-classroom.component.css"],
})
export class BookingRequestCreationByClassroomComponent implements OnInit {
  @Output() closeForm = new EventEmitter();
  @Output() bookingRequestCreated = new EventEmitter();

  currentDate: Date = new Date();
  calendarAlt = faCalendarAlt;

  site = "";
  building = "";
  classroom = "";
  startHour = "";
  endHour = "";
  supervisor = "";
  bookingDate: NgbDate = new NgbDate(
    this.currentDate.getFullYear(),
    this.currentDate.getMonth() + 1,
    this.currentDate.getDate()
  );

  sitesInfo: any;
  buildingList = [];
  classroomList = [];
  timeAvailabilities = [];
  supervisorsList = [];
  startOrEndTimes: number[] = [];

  showInvalidTimeRangeError: boolean = false;

  constructor(
    private siteService: SiteService,
    private classroomService: ClassroomService,
    private bookingRequestService: BookingRequestService,
    private authService: AuthService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.siteService.getEnabledSites().subscribe((data) => {
      this.sitesInfo = data as Object[];
      if (this.sitesInfo) {
        this.site = this.sitesInfo[0]["id"];
        this.updateBuildings();
      } else {
        this.site = "";
      }
    });
  }

  public createBookingRequest(values) {
    let bookingDate = new Date(
      this.bookingDate.year,
      this.bookingDate.month - 1,
      this.bookingDate.day
    );
    let bookingWrapper = {
      username: this.authService.currentUser["user_name"],
      classroomId: values.classroom,
      purpose: values["bookingPurpose"],
      date: formatDate(bookingDate, "yyyy-MM-dd", "en-US"),
      startHour: parseInt(values.startHour),
      endHour: parseInt(values.endHour),
      supervisorId: values.supervisor,
    };
    this.bookingRequestService.post(bookingWrapper).subscribe((data) => {
      this.toastService.show("Booking request successfully created.", {
        classname: "bg-success text-light",
        delay: 3000,
        autohide: true,
      });
      this.closeForm.emit();
      this.bookingRequestCreated.emit();
    });
  }

  public updateBuildings() {
    if (this.site === undefined || this.site === "") {
      this.buildingList = [];
      this.classroomList = [];
    } else {
      this.buildingList = this.sitesInfo.filter((s) => s["id"] == this.site)[0][
        "buildings"
      ];
    }
    if (this.buildingList && this.buildingList.length > 0) {
      this.building = this.buildingList[0]["id"];
    } else {
      this.building = "";
    }

    this.updateClassrooms();
  }

  public updateClassrooms() {
    if (this.building === undefined || this.building === "") {
      this.classroomList = [];
      this.timeAvailabilities = [];
      this.isTimeRangeValid();
    } else {
      let currentBuilding = this.buildingList.filter(
        (s) => s.id == this.building
      );
      if (currentBuilding) {
        this.classroomList = currentBuilding[0]["classrooms"];
      }
    }
    if (this.classroomList && this.classroomList.length > 0) {
      this.classroom = this.classroomList[0]["id"];
    } else {
      this.classroom = "";
    }
    this.loadAvailabilities();
    this.loadSupervisor();
    return this.classroomList;
  }

  public closeCreateRequestForm() {
    this.closeForm.emit();
  }

  public loadAvailabilities() {
    if (this.canLoadAvailabilities()) {
      this.classroomService
        .getClassroomAvailabilities(
          this.classroom,
          new Date(
            this.bookingDate.year,
            this.bookingDate.month - 1,
            this.bookingDate.day
          )
        )
        .subscribe((data) => {
          this.timeAvailabilities = data as Array<any>;
          this.loadTimes();
        });
    } else {
      this.timeAvailabilities = [];
      this.isTimeRangeValid();
      this.loadTimes();
    }
  }

  public loadSupervisor() {
    if (this.canLoadAvailabilities()) {
      this.classroomService
        .getClassroomSupervisors(this.classroom)
        .subscribe((data) => {
          this.supervisorsList = data as Array<any>;
          this.supervisor = this.supervisorsList[0]["id"];
        });
    } else {
      this.supervisorsList = [];
    }
  }

  public hasTimeAvailability() {
    return this.timeAvailabilities && this.timeAvailabilities.length > 0;
  }

  public displayHours(fullTime: string) {
    return fullTime.split(":")[0] + "H";
  }

  public loadTimes() {
    this.startOrEndTimes = [];
    if (this.timeAvailabilities && this.timeAvailabilities.length > 0) {
      this.timeAvailabilities.forEach((item) =>
        this.loadTimesFromInterval(item)
      );
    }
    if (this.startOrEndTimes.length > 0) {
      this.startHour = "" + this.startOrEndTimes[0];
      this.endHour = "" + this.startOrEndTimes[1];
    } else {
      this.startHour = "";
      this.endHour = "";
    }
  }

  private loadTimesFromInterval(timeInterval) {
    let start: number = parseInt(timeInterval["fromTime"].split(":")[0]);
    let end: number = parseInt(timeInterval["toTime"].split(":")[0]);
    while (start <= end) {
      this.startOrEndTimes.push(start);
      start++;
    }
  }

  public isTimeRangeValid() {
    if (this.timeAvailabilities.length == 0) {
      this.showInvalidTimeRangeError = false;
    } else {
      let cpt: number = 0;
      let checker = false;
      while (cpt < this.timeAvailabilities.length) {
        let currResult = this.isTimeRangeInTimeInterval(
          this.timeAvailabilities[cpt]
        );
        if (currResult) {
          checker = true;
          break;
        }
        cpt++;
      }
      this.showInvalidTimeRangeError = !checker;
    }
  }

  private isTimeRangeInTimeInterval(timeInterval) {
    let start: number = parseInt(timeInterval["fromTime"].split(":")[0]);
    let end: number = parseInt(timeInterval["toTime"].split(":")[0]);

    let startHourInt = parseInt(this.startHour);
    let endHourInt = parseInt(this.endHour);

    return (
      start <= startHourInt &&
      end >= startHourInt &&
      start <= endHourInt &&
      end >= endHourInt &&
      startHourInt < endHourInt
    );
  }

  private canLoadAvailabilities() {
    return !(
      !this.site ||
      this.site === "" ||
      !this.building ||
      this.building === "" ||
      !this.classroom ||
      this.classroom === ""
    );
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
}
