import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders, HttpResponse } from "@angular/common/http";
import { environment } from "../../../environments/environment";
import { Observable, throwError } from "rxjs";
import { map, catchError } from "rxjs/operators";
import { JwtHelperService } from "@auth0/angular-jwt";
import { UnauthorizedError } from "../../shared/exceptions/unauthorized-error";
import { AppError } from "../../shared/exceptions/app-error";

export const ROLE_ADMIN = "ROLE_ADMIN";
export const ROLE_SUPERVISOR = "ROLE_SUPERVISOR";
export const ROLE_GROUPLEADER = "ROLE_GROUPLEADER";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private jwtHelper: JwtHelperService;

  constructor(private http: HttpClient) {
    this.jwtHelper = new JwtHelperService();
  }

  login(credentials): Observable<boolean> {
    credentials["grant_type"] = "password";
    const AUTH_TOKEN_LINK = "/oauth/token";
    const basicAuth =
      "Basic " + btoa(environment.clientId + ":" + environment.clientPwd);
    const headersOptions = {
      headers: new HttpHeaders({
        "Content-Type": "application/x-www-form-urlencoded",
        Authorization: basicAuth,
      }),
    };

    return this.http
      .post(
        environment.authServerEndPoint + AUTH_TOKEN_LINK,
        AuthService.convertBodyToUrlEncodedFormat(credentials),
        headersOptions
      )
      .pipe(
        map((response) => {
          if (response["access_token"]) {
            localStorage.setItem("access_token", response["access_token"]);
            localStorage.setItem("refresh_token", response["refresh_token"]);
            return true;
          }
          return false;
        }),
        catchError(AuthService.handleError)
      );
  }

  logout() {
    localStorage.removeItem("access_token");
    localStorage.removeItem("refresh_token");
  }

  isLoggedIn() {
    let token = AuthService.getAccessToken();
    return token != null && !this.jwtHelper.isTokenExpired(token, 10);
  }

  get currentUser() {
    let token = AuthService.getAccessToken();
    if (!token) return null;
    return this.jwtHelper.decodeToken(token);
  }

  hasGroupLeaderRole() {
    return this.currentUser["authorities"].some(
      (item) => item === ROLE_GROUPLEADER
    );
  }

  hasSupervisorRole() {
    return this.currentUser["authorities"].some(
      (item) => item === ROLE_SUPERVISOR
    );
  }

  hasAdminRole() {
    return this.currentUser["authorities"].some((item) => item === ROLE_ADMIN);
  }

  public static handleError(error: HttpResponse<any>) {
    if (error.status === 401) {
      return throwError(new UnauthorizedError(error));
    } else {
      return throwError(new AppError(error));
    }
  }

  public static getAccessToken() {
    return localStorage.getItem("access_token");
  }

  private static convertBodyToUrlEncodedFormat(credentials) {
    let tab: Array<string> = [];
    for (let key in credentials) {
      tab.push(
        encodeURIComponent(key) + "=" + encodeURIComponent(credentials[key])
      );
    }
    return tab.join("&");
  }
}
