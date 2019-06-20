export interface Transaction {
  amount: number;
  ingestion: boolean;
  date: string;
  title: string;
  tagIds: string[];
  linkedUserID: string;
}
