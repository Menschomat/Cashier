import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

@Injectable({
  providedIn: "root"
})
export class UserService {
  apiURL: string = "/api/user";
  user: string;
  constructor(private httpClient: HttpClient) {
    this.httpClient.get<any>("/api/user/current").subscribe(user => {
      this.user = user;
    });
  }
  public getUser():any {
    if (this.user) {
      return this.user;
    } else {
      this.httpClient.get<any>("/api/user/current").subscribe(user => {
        return user;
      });
    }
  }
}
