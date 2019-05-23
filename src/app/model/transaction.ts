import { Tag } from './tag';

export interface Transaction {
  amount: Number;
  date: String;
  title: String;
  tagIds: String[];
}
