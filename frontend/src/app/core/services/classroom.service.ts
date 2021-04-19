import {Injectable} from '@angular/core';
import {CrudService} from "./crud.service";
import {environment} from "../../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {formatDate} from "@angular/common";

const CLASSROOMS_RESOURCE_LINK = environment.resourceServerEndPoint + '/classrooms';

@Injectable({
  providedIn: 'root'
})
export class ClassroomService extends CrudService {

  constructor(private httpClient: HttpClient) {
    super(httpClient, CLASSROOMS_RESOURCE_LINK);
  }

  getClassroomSupervisors(classroomId: string) {
    const customUrl = CLASSROOMS_RESOURCE_LINK + '/' + classroomId + '/supervisors';
    return this.get(customUrl);
  }

  getClassroomAvailabilities(classroomId: string, date: Date) {
    const customUrl = CLASSROOMS_RESOURCE_LINK + '/' + classroomId + '/availableTimeRanges';
    let requestParams = new HttpParams();
    requestParams = requestParams.append('date', formatDate(date, 'yyyy-MM-dd', 'en-US'));
    return this.get(customUrl, requestParams);
  }

}
