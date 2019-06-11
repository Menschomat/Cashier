import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { FrontendUser } from '../model/frontend-user';

@Injectable({
  providedIn: "root"
})
export class UserService {
  apiURL: string = "/api/user";
  user: string;
  constructor(private httpClient: HttpClient) {}
  public getUser(): any {
    return this.httpClient.get<any>("/api/user/current");
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
