import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { catchError } from 'rxjs/operators';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    console.log("Error interceptor working");
    return next.handle(request).pipe(catchError(err => {
      if(err.status === 401) {
        this.authService.logout();
        // redirect to login page
      }
      const error = err.error.message || err.statusText;
      return throwError(error);
    }));
  }
}
