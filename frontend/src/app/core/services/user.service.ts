import { Injectable } from "@angular/core";
import { environment } from "../../../environments/environment";
import { CrudService } from "./crud.service";
import { HttpClient } from "@angular/common/http";
import { AuthService } from "./auth.service";

const USER_RESOURCE_LINK = environment.resourceServerEndPoint + "/users";

@Injectable({
  providedIn: "root",
})
export class UserService extends CrudService {
  constructor(
    private httpClient: HttpClient,
    private authService: AuthService
  ) {
    super(httpClient, USER_RESOURCE_LINK);
  }

  getUsersByRole(userRole: string) {
    const customUrl = USER_RESOURCE_LINK + "/role/" + userRole;
    return this.get(customUrl);
  }

  updatePwd(oldPwd: string, newPwd: string) {
    const customUrl = USER_RESOURCE_LINK + "/pwd";
    const requestBody = {
      username: this.authService.currentUser["user_name"],
      oldPwd: oldPwd,
      newPwd: newPwd,
    };
    return this.put(requestBody, customUrl);
  }

  enableUser(userId: string) {
    const customUrl = USER_RESOURCE_LINK + "/enable";
    return this.put(userId, customUrl);
  }

  disableUser(userId: string) {
    const customUrl = USER_RESOURCE_LINK + "/disable";
    return this.put(userId, customUrl);
  }
}
