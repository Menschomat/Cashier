import { Transaction } from './transaction';
import { Tag } from './tag';

export interface NewTransaction {
    transaction:Transaction;
    tags:Tag[];
}