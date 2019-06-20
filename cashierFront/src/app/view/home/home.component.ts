import { Component, OnInit } from "@angular/core";
import { TransactionService } from "src/app/services/transaction.service";
import { Transaction } from "src/app/model/transaction-management/transaction";

@Component({
  selector: "app-home",
  templateUrl: "./home.component.html",
  styleUrls: ["./home.component.scss"]
})
export class HomeComponent implements OnInit {
  fromDate: Date;
  toDate: Date;
  constructor(private transactionService: TransactionService) {}

  transactions: Transaction[] = [];

  ngOnInit() {}
  dateChanged(datePair: any) {
    let now = new Date();
    this.fromDate = datePair.from;
    this.toDate = datePair.to;
    this.fromDate.setHours(22);
    this.fromDate.setMinutes(0);
    this.toDate.setHours(22);
    this.toDate.setMinutes(0);
    this.refreshData();
  }
  refreshData() {
    if (this.fromDate && this.toDate)
      this.transactionService
        .getFromTo(this.fromDate, this.toDate)
        .subscribe(res => {
          this.transactions = res;
        });
  }
}
