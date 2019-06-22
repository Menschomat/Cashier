import {
  Component,
  OnInit,
  Input,
  SimpleChange,
  Output,
  EventEmitter,
  ViewChild
} from "@angular/core";
import { OverviewData } from "src/app/view/home/overview-card/model/overview-data";
import { MatDialogConfig, MatDialog } from "@angular/material/dialog";
import { MatPaginator } from "@angular/material/paginator";
import { MatSort } from "@angular/material/sort";
import { MatTableDataSource } from "@angular/material/table";
import { NewTransactionDialogComponent } from "../../../components/new-transaction-dialog/new-transaction-dialog.component";
import {
  faPlus,
  faTrashAlt,
  faPlusCircle,
  faTimes,
  faMinusCircle
} from "@fortawesome/free-solid-svg-icons";
import { TransactionService } from "src/app/services/transaction.service";
import { TagService } from "src/app/services/tag.service";
import { Tag } from "src/app/model/hashtag-system/tag";
import { TagEditorComponent } from "../../../components/tag-editor/tag-editor.component";
import { SelectionModel } from "@angular/cdk/collections";
import { Transaction } from "src/app/model/transaction-management/transaction";
import { StatusServiceService } from "src/app/services/status-service.service";

@Component({
  selector: "app-overview-card",
  templateUrl: "./overview-card.component.html",
  styleUrls: ["./overview-card.component.scss"]
})
export class OverviewCardComponent implements OnInit {
  faPlus = faPlus;
  faPlusC = faPlusCircle;
  faMinusC = faMinusCircle;
  faTrash = faTrashAlt;
  loading = false;
  fromDate: Date;
  toDate: Date;

  @Input()
  data: Transaction[] = [];

  @Output() datePair = new EventEmitter();
  @Output() reloadFromServer = new EventEmitter();

  displayedColumns: string[] = [
    "select",
    "type",
    "dateTime",
    "title",
    "amount",
    "tags"
  ];
  selection = new SelectionModel<Transaction>(true, []);
  dataSource = new MatTableDataSource<Transaction>();
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: false }) sort: MatSort;
  constructor(
    private dialog: MatDialog,
    private transactionService: TransactionService,
    private tagService: TagService,
    private statusService: StatusServiceService
  ) {}
  ngOnInit() {
    this.dataSource.sortingDataAccessor = (item, property) => {
      switch (property) {
        case 'dateTime': return Date.parse(item.date.split("+")[0].substring(0, item.date.split("+")[0].length - 4));
        default: return item[property];
      }
    };
    this.fromDate = new Date();
    this.toDate = new Date();
    this.fromDate.setMonth(this.toDate.getMonth() - 1);
    this.dateChanged()
    this.loading = true;
    this.refreshData();
  }
  ngOnChanges(changes: SimpleChange) {
    this.refreshData();
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

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
        this.statusService.sendMessage({ saved: false });
        this.tagService.addTags(result.tags);
        this.tagService.saveAndUpdateTagList();
        this.transactionService
          .addSingleTransaction(result.transaction)
          .subscribe(() => {
            this.reloadFromServer.emit();
          });
      }
    });
  }
  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  refreshData() {
    this.dataSource.data = this.data;
    this.loading = false;
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }
  deleteTransactions(tList: Transaction[]) {
    this.statusService.sendMessage({ saved: false });
    tList.forEach(tag => {
      this.selection.deselect(tag);
    });
    this.transactionService.deleteTransactions(tList).subscribe(() => {
      this.reloadFromServer.emit();
    });
  }
  openEdit(tag: Tag) {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;

    dialogConfig.data = tag;

    let dialogRef = this.dialog.open(TagEditorComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(result => {
      this.reloadFromServer.emit();
    });
  }

  dateChanged() {
    this.datePair.emit({ from: this.fromDate, to: this.toDate });
  }
}
