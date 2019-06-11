import { Component, OnInit } from "@angular/core";
import { UserService } from "src/app/services/user.service";
import { UserAdminService } from "src/app/services/user-admin.service";
import { FrontendUser } from "src/app/model/frontend-user";
import { MatTableDataSource } from "@angular/material";
import { SelectionModel } from "@angular/cdk/collections";
import { faTrash, faPlus, faTrashAlt } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-user-list",
  templateUrl: "./user-list.component.html",
  styleUrls: ["./user-list.component.scss"]
})
export class UserListComponent implements OnInit {
  constructor(private userAdminService: UserAdminService) {}
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
    this.userAdminService.deleteUseres(toDel).subscribe(users => {
      this.dataSource.data = users;
      this.users = users;
    });
  }
  openNewUser() {}
}
