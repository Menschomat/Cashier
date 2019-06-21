import { Transaction } from 'src/app/model/transaction-management/transaction';

export interface OverviewData {
    totalIn :number;
    totalOut : number;
    lastTransactions: Transaction[];
}
