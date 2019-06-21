import { HttpClient } from "@angular/common/http";
import { Component, ViewChild, AfterViewInit } from "@angular/core";
import { MatPaginator } from "@angular/material/paginator";
import { MatSort } from "@angular/material/sort";
import { merge, Observable, of as observableOf } from "rxjs";
import { catchError, map, startWith, switchMap } from "rxjs/operators";
import { TransactionService } from "src/app/services/transaction.service";
import { Transaction } from "src/app/model/transaction-management/transaction";
import { TagService } from "src/app/services/tag.service";
import { NewTransactionDialogComponent } from "src/app/components/new-transaction-dialog/new-transaction-dialog.component";
import { MatDialogConfig, MatDialog } from "@angular/material";
import { StatusServiceService } from "src/app/services/status-service.service";
import {
  faPlus,
  faTrash,
  faTrashAlt,
  faTimes,
  faPlusCircle,
  faMinusCircle
} from "@fortawesome/free-solid-svg-icons";
import { SelectionModel } from "@angular/cdk/collections";

/**
 * @title Table retrieving data through HTTP
 */
@Component({
  selector: "app-transactions-component",
  styleUrls: ["transactions.component.scss"],
  templateUrl: "transactions.component.html"
})
export class TransactionsComponent implements AfterViewInit {
  displayedColumns: string[] = [
    "select",
    "type",
    "date",
    "title",
    "amount",
    "tags"
  ];
  data: Transaction[] = [];
  selection = new SelectionModel<String>(true, []);
  faPlusC = faPlusCircle;
  faMinusC = faMinusCircle;
  faPlus = faPlus;
  faTrash = faTrashAlt;
  faTimes = faTimes;
  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private transactionService: TransactionService,
    private tagService: TagService,
    private statusService: StatusServiceService,
    private dialog: MatDialog
  ) {}

  ngAfterViewInit() {
    // this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this.transactionService!.getPaged(
            this.paginator.pageSize,
            this.paginator.pageIndex,
            this.sort.active,
            this.sort.direction
          );
        }),
        map(data => {
          this.isLoadingResults = false;
          this.isRateLimitReached = false;
          this.resultsLength = data.totalEntries;
          return data.transactions;
        }),
        catchError(err => {
          this.isLoadingResults = false;
          this.isRateLimitReached = true;
          return observableOf([]);
        })
      )
      .subscribe(data => (this.data = data));
  }
  getTagForTagID(tID: string) {
    return this.tagService.getTag(tID);
  }
  deleteTransactions(tList) {
    this.statusService.sendMessage({ saved: false });
    tList.forEach(id => {
      this.selection.deselect(id);
    });
    this.transactionService.deleteTransactionsById(tList).subscribe(res => {
      this.ngAfterViewInit();
      this.statusService.sendMessage({ saved: true });
    });
  }
  openNewTransaction() {
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
        this.isLoadingResults = true;
        this.statusService.sendMessage({ saved: false });
        this.tagService.addTags(result.tags);
        this.tagService.saveAndUpdateTagList();
        this.transactionService
          .addSingleTransaction(result.transaction)
          .subscribe(res => {
            this.ngAfterViewInit();
            this.isLoadingResults = false;
            this.statusService.sendMessage({ saved: true });
          });
      }
    });
  }
}
