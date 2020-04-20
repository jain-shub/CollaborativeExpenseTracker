import { Injectable } from "@angular/core";
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from "@angular/common/http";
import { Observable } from "rxjs";

import { UserService } from "../services/user.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  userService: UserService;
  constructor(userService: UserService) {
    this.userService = userService;
  }

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    if (this.userService.getToken()) {
      request = request.clone({
        setHeaders: {
          "Content-Type": "application/json; charset=utf-8",
          Accept: "application/json",
          Authorization: `Bearer ${this.userService.getToken()}`,
        },
      });
    }

    return next.handle(request);
  }
}
