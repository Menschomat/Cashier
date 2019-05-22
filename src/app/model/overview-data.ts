import { Transaction } from './transaction';

export interface OverviewData {
    totalIn :Number;
    totalOut : Number;
    lastTransactions: Transaction[];
}
