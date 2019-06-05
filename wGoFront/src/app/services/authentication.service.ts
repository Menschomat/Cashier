import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class AuthenticationService {
    constructor(private http: HttpClient) { }

    login(username: string, password: string) {
        return this.http.post<any>(`/api/users/login`, { "username":username, "password":password }, {observe: 'response'})
            .pipe(map(resp => {
                console.log(resp);
                
                // login successful if there's a jwt token in the response
                if (resp.headers.get('Authorization')) {
                    // store user details and jwt token in local storage to keep user logged in between page refreshes
                    localStorage.setItem('cashierUserToken', resp.headers.get('Authorization'));
                }

                return resp.headers.get('Authorization');
            }));
    }

    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem('cashierUserToken');
    }
}