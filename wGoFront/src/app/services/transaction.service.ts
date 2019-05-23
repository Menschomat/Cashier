import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Transaction } from '../model/transaction';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  apiURL: string = 'http://localhost:8080/transaction';

  constructor(private httpClient: HttpClient) { }
  public getLatesTransactions(){
    return this.httpClient.get<Transaction[]>(`${this.apiURL}/all`);
}
}
