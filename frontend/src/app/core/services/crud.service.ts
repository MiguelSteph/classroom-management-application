import {Inject} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {AuthService} from "./auth.service";

export class CrudService {

  constructor(private http:HttpClient, @Inject(String) private resourceUrl: string) { }

  get(customUrl?: string, queryParams?: HttpParams) : Observable<Object> {
    return this.http
      .get(
        customUrl ? customUrl : this.resourceUrl,
        {params: queryParams}
      ).pipe(
        catchError(AuthService.handleError)
      );
  }

  post(body) {
    return this.http
      .post(this.resourceUrl, body)
      .pipe(
        catchError(AuthService.handleError)
      );
  }

  put(body, customUrl?: string, customHeader?: HttpHeaders) {
    if (customHeader) {
      return this.http
        .put(customUrl ? customUrl : this.resourceUrl, body, {headers: customHeader})
        .pipe(
          catchError(AuthService.handleError)
        );
    } else {
      return this.http
        .put(customUrl ? customUrl : this.resourceUrl, body)
        .pipe(
          catchError(AuthService.handleError)
        );
    }
  }

}
