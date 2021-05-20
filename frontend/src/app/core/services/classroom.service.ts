import { Injectable } from "@angular/core";
import { CrudService } from "./crud.service";
import { environment } from "../../../environments/environment";
import { HttpClient, HttpParams } from "@angular/common/http";
import { formatDate } from "@angular/common";
import { AuthService } from "./auth.service";

const CLASSROOMS_RESOURCE_LINK =
  environment.resourceServerEndPoint + "/classrooms";

@Injectable({
  providedIn: "root",
})
export class ClassroomService extends CrudService {
  constructor(
    private httpClient: HttpClient,
    private authService: AuthService
  ) {
    super(httpClient, CLASSROOMS_RESOURCE_LINK);
  }

  getClassroomSupervisors(classroomId: string) {
    const customUrl =
      CLASSROOMS_RESOURCE_LINK + "/" + classroomId + "/supervisors";
    return this.get(customUrl);
  }

  getClassroomAllCurrentAvailabilities(classroomId: string) {
    const customUrl =
      CLASSROOMS_RESOURCE_LINK +
      "/" +
      classroomId +
      "/allCurrentAvailabilities";
    return this.get(customUrl);
  }

  getClassroomAvailabilities(classroomId: string, date: Date) {
    const customUrl =
      CLASSROOMS_RESOURCE_LINK + "/" + classroomId + "/availableTimeRanges";
    let requestParams = new HttpParams();
    requestParams = requestParams.append(
      "date",
      formatDate(date, "yyyy-MM-dd", "en-US")
    );
    return this.get(customUrl, requestParams);
  }

  shrinkClassroomAvailabilities(classroomId: string, fromDate, toDate) {
    const customUrl =
      CLASSROOMS_RESOURCE_LINK + "/" + classroomId + "/shrinkAvailability";
    const requestBody = {
      username: this.authService.currentUser["user_name"],
      fromDate: fromDate,
      toDate: toDate,
    };
    return this.put(requestBody, customUrl);
  }
}
