import { Tag } from './tag';

export interface Transaction {
  amount: Number;
  dateTime: String;
  title: String;
  tags: Tag[];
}
