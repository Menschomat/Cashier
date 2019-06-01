import { Component, OnInit, Input } from "@angular/core";
import { OverviewData } from "src/app/model/overview-data";
import {
  MatDialogConfig,
  MatDialog,
  MatTableDataSource
} from "@angular/material";
import { NewTransactionDialogComponent } from "../new-transaction-dialog/new-transaction-dialog.component";
import { faPlus, faTrashAlt } from "@fortawesome/free-solid-svg-icons";
import { TransactionService } from "src/app/services/transaction.service";
import { TagService } from "src/app/services/tag.service";
import { Tag } from "src/app/model/tag";
import { TagEditorComponent } from "../tag-editor/tag-editor.component";
import { SelectionModel } from "@angular/cdk/collections";
import { Transaction } from "src/app/model/transaction";

@Component({
  selector: "app-overview-card",
  templateUrl: "./overview-card.component.html",
  styleUrls: ["./overview-card.component.scss"]
})
export class OverviewCardComponent implements OnInit {
  faPlus = faPlus;
  faTrash = faTrashAlt;
  loading = false;
  @Input()
  data = {} as OverviewData;

  displayedColumns: string[] = [
    "select",
    "dateTime",
    "title",
    "amount",
    "tags"
  ];
  selection = new SelectionModel<Transaction>(true, []);
  dataSource = new MatTableDataSource<Transaction>();
  constructor(
    private dialog: MatDialog,
    private transactionService: TransactionService,
    private tagService: TagService
  ) {}
  ngOnInit() {
    this.loading = true;
    this.transactionService.getLatesTransactions().subscribe(res => {
      this.data.lastTransactions = res;
      this.dataSource.data = res;
      this.loading = false;
    });
  }

  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected()
      ? this.selection.clear()
      : this.dataSource.data.forEach(row => this.selection.select(row));
  }

  getTagForTagID(tID: string) {
    return this.tagService.getTag(tID);
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
        this.loading = true;
        this.tagService.addTags(result.tags);
        this.tagService.saveAndUpdateTagList();
        this.transactionService
          .addSingleTransaction(result.transaction)
          .subscribe(res => {
            this.data.lastTransactions = res;
            this.dataSource.data = res;
            this.loading = false;
          });
      }
    });
  }
  deleteTransactions(tList: Transaction[]) {
    tList.forEach(tag => {
      this.selection.deselect(tag);
    })
    this.transactionService.deleteTransactions(tList).subscribe(res =>{
      this.data.lastTransactions = res;
      this.dataSource.data = res;
    })
    console.log(tList);
  }
  openEdit(tag: Tag) {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;

    dialogConfig.data = tag;

    let dialogRef = this.dialog.open(TagEditorComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(result => {});
  }
}
