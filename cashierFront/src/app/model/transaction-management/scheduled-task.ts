import { Transaction } from './transaction';

export interface ScheduledTask {
    id:string;
    userID:string;
    cronTab:string;
    transaction:Transaction;
}
