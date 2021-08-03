import { DbUser } from '../user-management/db-user';
import { Transaction } from '../transaction-management/transaction';

export interface Tag {
  id: any;
  transactions:Transaction[];
  title: string;
  color: string;
  user: DbUser;
}
