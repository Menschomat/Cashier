import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Transaction } from "../model/transaction";
import { UserService } from "./user.service";
import { Observable } from "rxjs";
import { TransactionResponse } from "../model/TransactionResponse";

@Injectable({
  providedIn: "root"
})
export class TransactionService {
  apiURL: string = "/api/transaction";

  constructor(private httpClient: HttpClient, private uService: UserService) {
    this.httpClient.get<any>("/api/init/checkup/firstTime").subscribe();
  }
  public getLatesTransactions() {
    return this.httpClient.get<Transaction[]>(`${this.apiURL}/all`);
  }
  public addSingleTransaction(toAdd: Transaction) {
    toAdd.linkedUserID = this.uService.getUser().id;
    return this.httpClient.post<Transaction[]>(`${this.apiURL}`, toAdd);
  }
  public deleteTransactions(toDelete: Transaction[]) {
    const httpOptions = {
      headers: new HttpHeaders({
        "Content-Type": "application/json"
      }),
      body: toDelete
    };
    return this.httpClient.delete<Transaction[]>(`${this.apiURL}`, httpOptions);
  }
  public deleteTransactionsById(toDelete: string[]) {
    const httpOptions = {
      headers: new HttpHeaders({
        "Content-Type": "application/json"
      }),
      body: toDelete
    };
    return this.httpClient.delete<Transaction[]>(`${this.apiURL}/id`, httpOptions);
  }
  public getPaged(
    size: number,
    page: number,
    sortBy: string,
    sortDir: string
  ): Observable<TransactionResponse> {
    return this.httpClient.get<TransactionResponse>(
      `${
        this.apiURL
      }/paged?size=${size}&page=${page}&sortBy=${sortBy}&sortDir=${sortDir}`
    );
  }
}
