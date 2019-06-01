import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Transaction } from "../model/transaction";

@Injectable({
  providedIn: "root"
})
export class TransactionService {
  apiURL: string = "http://localhost:8080/transaction";

  constructor(private httpClient: HttpClient) {}
  public getLatesTransactions() {
    return this.httpClient.get<Transaction[]>(`${this.apiURL}/all`);
  }
  public addSingleTransaction(toAdd: Transaction) {
    return this.httpClient.post<Transaction[]>(`${this.apiURL}`, toAdd);
  }
  public deleteTransactions(toDelete: Transaction[]) {
    const httpOptions = {
      headers: new HttpHeaders({ "Content-Type": "application/json" }),
      body: toDelete
    };
    return this.httpClient.delete<Transaction[]>(`${this.apiURL}`, httpOptions);
  }
}
