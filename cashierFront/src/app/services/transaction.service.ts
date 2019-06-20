import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Transaction } from "../model/transaction-management/transaction";
import { UserService } from "./user.service";
import { Observable } from "rxjs";
import { TransactionResponse } from "../model/transaction-management/TransactionResponse";

@Injectable({
  providedIn: "root"
})
export class TransactionService {
  apiURL: string = "/api/transaction";

  constructor(private httpClient: HttpClient, private uService: UserService) {
  }
  public getLatesTransactions() {
    return this.httpClient.get<Transaction[]>(`${this.apiURL}/latest`);
  }
  public addSingleTransaction(toAdd: Transaction) {
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
    return this.httpClient.delete<Transaction[]>(
      `${this.apiURL}/id`,
      httpOptions
    );
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
  public getFromToPaged(
    from: Date,
    to: Date,
    size: number,
    page: number,
    sortBy: string,
    sortDir: string
  ): Observable<TransactionResponse> {
    return this.httpClient.get<TransactionResponse>(
      `${
        this.apiURL
      }/date?size=${size}&page=${page}&sortBy=${sortBy}&sortDir=${sortDir}&from=${from.toISOString()}&to=${to.toISOString()}`
    );
  }
  public getFromTo(from: Date, to: Date): Observable<Transaction[]> {
    console.log(from, to);
    
    return this.httpClient.get<Transaction[]>(
      `${this.apiURL}/date/all?from=${from.toISOString()}&to=${to.toISOString()}`
    );
  }
}
