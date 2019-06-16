export interface Transaction {
  amount: number;
  date: string;
  title: string;
  tagIds: string[];
  linkedUserID: string;
}
