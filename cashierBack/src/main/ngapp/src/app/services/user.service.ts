import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { FrontendUser } from '../model/user-management/frontend-user';
import { Subscribable } from 'rxjs';

@Injectable({
  providedIn: "root"
})
export class UserService {
  apiURL: string = "/api/user";
  user: string;
  constructor(private httpClient: HttpClient) {}
  public getUser() {
    return this.httpClient.get<FrontendUser>("/api/user/current");
  }
  public updateUser(user:FrontendUser) {
    return this.httpClient.post<FrontendUser>("/api/user/current",user);
  }
  public changePassword(oldPW: string, newPW: string): any {
    return this.httpClient.post<any>(
      "/api/user/password",
      {
        oldPW: oldPW,
        newPW: newPW
      },
      { responseType: "text" as "json" }
    );
  }

}
