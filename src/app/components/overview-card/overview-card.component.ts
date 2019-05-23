import { Component, OnInit, Input } from "@angular/core";
import { OverviewData } from "src/app/model/overview-data";
import { MatDialogConfig, MatDialog } from "@angular/material";
import { NewTransactionDialogComponent } from "../new-transaction-dialog/new-transaction-dialog.component";
import { faPlus } from '@fortawesome/free-solid-svg-icons';

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

  @Input()
  data = {} as OverviewData;

  displayedColumns: string[] = ["dateTime", "title", "amount", "tags"];
  constructor(private dialog: MatDialog) {}

  ngOnInit() {}
  openDialog() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.minWidth = "40%";

    this.dialog.open(NewTransactionDialogComponent, dialogConfig);
  }
}
