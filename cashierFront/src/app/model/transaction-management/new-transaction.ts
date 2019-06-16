import { Transaction } from './transaction';
import { Tag } from '../hashtag-system/tag';


export interface NewTransaction {
    transaction:Transaction;
    tags:Tag[];
}
