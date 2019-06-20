import { Component, OnInit } from "@angular/core";
import { SelectionModel } from "@angular/cdk/collections";
import { faPlus, faTrashAlt, faTimes } from "@fortawesome/free-solid-svg-icons";
import { ScheduledTask } from "src/app/model/transaction-management/scheduled-task";
import { SchedulerService } from "src/app/services/scheduler.service";
import { TagService } from "src/app/services/tag.service";
import cronstrue from "cronstrue/i18n";
import { MatDialog, MatDialogConfig } from "@angular/material";
import { NewScheduledTaskComponent } from "./new-scheduled-task/new-scheduled-task.component";
import { StatusServiceService } from "src/app/services/status-service.service";

@Component({
  selector: "app-scheduled-task-card",
  templateUrl: "./scheduled-task-card.component.html",
  styleUrls: ["./scheduled-task-card.component.scss"]
})
export class ScheduledTaskCardComponent implements OnInit {
  selection = new SelectionModel<String>(true, []);
  data: ScheduledTask[] = [];
  displayedColumns: string[] = [
    "select",
    "cronTab",
    "transactionTitle",
    "transactionAmount",
    "transactionTags"
  ];
  faPlus = faPlus;
  faTrash = faTrashAlt;
  faTimes = faTimes;
  constructor(
    private schedulerService: SchedulerService,
    private dialog: MatDialog,
    private tagService: TagService,
    private statusService: StatusServiceService
  ) {}

  ngOnInit() {
    this.schedulerService.getAllTasks().subscribe(tasks => {
      this.data = tasks;
    });
  }
  getTagForTagID(tID: string) {
    return this.tagService.getTag(tID);
  }
  cronToText(cron: string) {
    return cronstrue.toString(cron, {
      locale: "en",
      use24HourTimeFormat: true
    });
  }
  openDialog() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.minWidth = "40%";

    let dialogRef = this.dialog.open(NewScheduledTaskComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.statusService.sendMessage({ saved: false });
        this.tagService.addTags(result.tags);
        this.tagService.saveAndUpdateTagList();
        this.schedulerService.addTask(result.task).subscribe(res => {
          this.data = res;
          this.statusService.sendMessage({ saved: true });
        });
      }
    });
  }
  delete(tList: string[]) {
    this.statusService.sendMessage({ saved: false });
    tList.forEach(id => {
      this.selection.deselect(id);
    });
    this.schedulerService.deleteTasks(tList).subscribe(res => {
      this.data = res;
      this.statusService.sendMessage({ saved: true });
    });
  }
}
