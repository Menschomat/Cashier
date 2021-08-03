import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthenticationService } from '../services/authentication.service';
import { Router } from '@angular/router';



@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    constructor(private authenticationService: AuthenticationService,private router : Router) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(catchError(err => {
            if (err.status === 401 && err.error !== "CURRENT_WRONG") {
                // auto logout if 401 response returned from api
                this.authenticationService.logout();
                location.reload(true);
            }else if(err.status === 403){
                this.authenticationService.logout();
                location.reload(true);
            }else if(err.status === 400){
                this.authenticationService.logout();
                this.router.navigate(['/login'])
  .then(() => {
    window.location.reload();
    return throwError(error);
  });
            }
            
            const error = err.error.message || err.statusText;
            return throwError(error);
        }))
    }
    redirectTo(uri:string){
        this.router.navigateByUrl('/DummyComponent', {skipLocationChange: true}).then(()=>
        this.router.navigate([uri]));}
}