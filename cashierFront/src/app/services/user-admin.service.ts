import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { DbUser } from '../model/db-user';
import { FrontendUser } from '../model/frontend-user';
import { Subscribable } from 'rxjs';

@Injectable({
  providedIn: "root"
})
export class UserAdminService {
  apiURL: string = "/api/user";
  user: string;
  constructor(private httpClient: HttpClient) {}
  public getAllUsers(): Subscribable<FrontendUser[]> {
    return this.httpClient.get<FrontendUser[]>("/api/user/all");
  }
  public saveAndUpdateUsers(users:DbUser[]): Subscribable<FrontendUser[]>{
    return this.httpClient.post<FrontendUser[]>("/api/user",users);
  }
}
