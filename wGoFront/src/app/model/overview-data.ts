import { Transaction } from './transaction';

export interface OverviewData {
    totalIn :number;
    totalOut : number;
    lastTransactions: Transaction[];
}
