import { Component, OnInit } from "@angular/core";
import { UserService } from "src/app/services/user.service";
import { UserAdminService } from "src/app/services/user-admin.service";
import { FrontendUser } from "src/app/model/frontend-user";
import {
  MatTableDataSource,
  MatDialogConfig,
  MatDialog,
  MatSnackBar
} from "@angular/material";
import { SelectionModel } from "@angular/cdk/collections";
import { faTrash, faPlus, faTrashAlt } from "@fortawesome/free-solid-svg-icons";
import { UserEditDialogComponent } from "../user-edit-dialog/user-edit-dialog.component";
import { HttpErrorResponse } from "@angular/common/http";
import { DbUser } from "src/app/model/db-user";

@Component({
  selector: "app-user-list",
  templateUrl: "./user-list.component.html",
  styleUrls: ["./user-list.component.scss"]
})
export class UserListComponent implements OnInit {
  constructor(
    private dialog: MatDialog,
    private userAdminService: UserAdminService,
    private snackBar: MatSnackBar
  ) {}
  users: FrontendUser[] = [];
  dataSource = new MatTableDataSource<FrontendUser>();
  selection = new SelectionModel<FrontendUser>(true, []);
  faTrash = faTrashAlt;
  faPlus = faPlus;

  displayedColumns: string[] = [
    "select",
    "uname",
    "name",
    "surname",
    "birth",
    "role"
  ];
  ngOnInit() {
    this.userAdminService.getAllUsers().subscribe(users => {
      this.dataSource.data = users;
      this.users = users;
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
  deleteUsers(toDel: FrontendUser[]) {
    toDel.forEach(u => {
      this.selection.deselect(u);
    });
    this.userAdminService.deleteUseres(toDel).subscribe(users => {
      this.dataSource.data = users;
      this.users = users;
    });
  }
  openSnackBar(message: string, action: string, panelclass: string) {
    this.snackBar.open(message, action, {
      duration: 10000,
      verticalPosition: "top",
      panelClass: [panelclass]
    });
  }

  openNewUser() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.minWidth = "40%";

    let dialogRef = this.dialog.open(UserEditDialogComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(result => {
      const toAdd: DbUser = result;
      if (toAdd) {
        this.userAdminService.saveAndUpdateUsers([toAdd]).subscribe(
          users => {
            this.dataSource.data = users;
            this.users = users;
          },
          (error: HttpErrorResponse) => {
            this.openSnackBar(
              `User ${toAdd.username} existiert bereits!`,
              "Close",
              "error-dialog"
            );
          }
        );
      }
    });
  }
}
