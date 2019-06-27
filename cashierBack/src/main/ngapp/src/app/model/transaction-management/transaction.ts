import { DbUser } from '../user-management/db-user';
import { Tag } from '../hashtag-system/tag';

export interface Transaction {
  id: string;
  amount: number;
  ingestion: boolean;
  date: string;
  title: string;
  tags: Tag[];
  user: DbUser;
}
