import { Transaction } from './transaction';

export interface TransactionResponse {
    totalPages:number;
    totalEntries:number;
    transactions:Transaction[];
  }
  