import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { DbUser } from '../model/db-user';

@Injectable({
  providedIn: "root"
})
export class UserAdminService {
  apiURL: string = "/api/user";
  user: string;
  constructor(private httpClient: HttpClient) {}
  public getAllUsers(): any {
    return this.httpClient.get<any>("/api/user/all");
  }
  public saveAndUpdateUsers(users:DbUser[]){

  }
}
