import { Component, OnInit, Input } from "@angular/core";
import { OverviewData } from "src/app/model/overview-data";
import { MatDialogConfig, MatDialog } from "@angular/material";
import { NewTransactionDialogComponent } from "../new-transaction-dialog/new-transaction-dialog.component";
import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { TransactionService } from "src/app/services/transaction.service";
import { TagService } from "src/app/services/tag.service";

export interface PeriodicElement {
  name: string;
  position: number;
  weight: number;
  symbol: string;
}

@Component({
  selector: "app-overview-card",
  templateUrl: "./overview-card.component.html",
  styleUrls: ["./overview-card.component.scss"]
})
export class OverviewCardComponent implements OnInit {
  faPlus = faPlus;
  loading = false;
  @Input()
  data = {} as OverviewData;

  displayedColumns: string[] = ["dateTime", "title", "amount", "tags"];
  constructor(
    private dialog: MatDialog,
    private transactionService: TransactionService,
    private tagService: TagService
  ) {}
  ngOnInit() {
    this.loading = true;
    this.transactionService.getLatesTransactions().subscribe(res => {
      this.data.lastTransactions = res;
      this.loading = false;
    });
  }
  getTagForTagID(tID:string){
    return this.tagService.getTag(tID);
  }
  openDialog() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.minWidth = "40%";

    let dialogRef = this.dialog.open(
      NewTransactionDialogComponent,
      dialogConfig
    );
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loading = true;
        console.log(result);
        this.tagService.addTags(result.tags);
        this.tagService.saveAndUpdateTagList();
        this.transactionService
          .addSingleTransaction(result.transaction)
          .subscribe(res => {
            this.data.lastTransactions = res;
            this.loading = false;
          });
      }
    });
  }
}
