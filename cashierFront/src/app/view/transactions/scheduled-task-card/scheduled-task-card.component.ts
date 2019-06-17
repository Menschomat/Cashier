import { Component, OnInit } from '@angular/core';
import { SelectionModel } from '@angular/cdk/collections';
import { faPlus, faTrashAlt, faTimes } from '@fortawesome/free-solid-svg-icons';
import { ScheduledTask } from 'src/app/model/transaction-management/scheduled-task';
import { SchedulerService } from 'src/app/services/scheduler.service';
import { TagService } from 'src/app/services/tag.service';
import cronstrue from 'cronstrue/i18n';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { NewScheduledTaskComponent } from './new-scheduled-task/new-scheduled-task.component';

@Component({
  selector: 'app-scheduled-task-card',
  templateUrl: './scheduled-task-card.component.html',
  styleUrls: ['./scheduled-task-card.component.scss']
})
export class ScheduledTaskCardComponent implements OnInit {
  selection = new SelectionModel<String>(true, []);
  data: ScheduledTask[] = [];
  displayedColumns: string[] = ["select", "cronTab", "transactionTitle", "transactionAmount", "transactionTags"];
  faPlus = faPlus;
  faTrash = faTrashAlt;
  faTimes = faTimes;
  constructor(
    private schedulerService: SchedulerService,
    private tagService: TagService,
    private dialog: MatDialog
  ) { }

  ngOnInit() {
    this.schedulerService.getAllTasks().subscribe(tasks => {
      this.data = tasks;
    })
  }
  getTagForTagID(tID: string) {
    return this.tagService.getTag(tID);
  }
  crontToText(cron: string) {
    
    return cronstrue.toString(cron, {  locale: "de",use24HourTimeFormat: true });
  }
  openDialog() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.minWidth = "40%";

    let dialogRef = this.dialog.open(
      NewScheduledTaskComponent,
      dialogConfig
    );
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
      }
    });
  }

}
