import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StatusServiceService } from '../services/status-service.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor() {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = localStorage.getItem('cashierUserToken');
    req = req.clone({
      setHeaders: {
        'Authorization': `${token}`
      },
    });

    return next.handle(req);
  }
}
// export class JwtInterceptor implements HttpInterceptor {

//     private APIToken = null;
//     private defaultApplicationHeaders = {
//         'Content-Type': 'application/json'
//     }

//     buildRequestHeaders():HttpHeaders {

//         let headers = this.defaultApplicationHeaders;
//         // set API-Token if available
//         if(this.APIToken !== null) {
//             let authHeaderTpl = `${this.APIToken}`;
//             headers['Authorization'] = authHeaderTpl
//         }

//         return new HttpHeaders(headers);
//     }

//     constructor() {
//         this.APIToken = localStorage.getItem('cashierUserToken')
//         console.log(this.APIToken);
//     }

//     intercept(req: HttpRequest<any>, next: HttpHandler) {
       
//         const headers = this.buildRequestHeaders();
//         const authReq = req.clone({ setHeaders:{
//             Authorization:this.APIToken
//         } });
//         console.log(authReq);

//         return next.handle(authReq);
//     }
// }