import { Tag } from './tag';

export interface Transaction {
  amount: number;
  date: string;
  title: string;
  tagIds: string[];
}
