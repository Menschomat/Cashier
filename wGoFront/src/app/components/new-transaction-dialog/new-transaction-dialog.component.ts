import { Component, OnInit } from "@angular/core";
import { MatDialogRef } from "@angular/material";
import { faTimes } from "@fortawesome/free-solid-svg-icons";
import { COMMA, ENTER, SPACE, SEMICOLON } from "@angular/cdk/keycodes";
import { MatChipInputEvent } from "@angular/material";
import { Tag } from "src/app/model/tag";
import { TagService } from "src/app/services/tag.service";
import { NewTransaction } from "src/app/model/new-transaction";
import { Transaction } from "src/app/model/transaction";
@Component({
  selector: "app-new-transaction-dialog",
  templateUrl: "./new-transaction-dialog.component.html",
  styleUrls: ["./new-transaction-dialog.component.scss"]
})
export class NewTransactionDialogComponent implements OnInit {
  output = {} as NewTransaction;
  constructor(
    private dialogRef: MatDialogRef<NewTransactionDialogComponent>,
    private tagService: TagService
  ) {
    this.output.tags = [];
    this.output.transaction = {} as Transaction;
    this.output.transaction.tagIds = [];
    this.output.transaction.amount = 0;
    this.output.transaction.title = "";
    this.output.transaction.date = "";
  }

  faClose = faTimes;
  visible = true;
  selectable = true;
  removable = true;
  addOnBlur = true;
  readonly separatorKeysCodes: number[] = [ENTER, SPACE, COMMA, SEMICOLON];

  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    // Add our fruit
    if ((value || "").trim()) {
      var found = false;
      for (var i = 0; i < this.output.tags.length; i++) {
        if (this.output.tags[i].title == value.trim()) {
          found = true;
          break;
        }
      }
      if (!found) {
        this.output.tags.push({
          title: value.trim(),
          color: this.tagService.getColor()
        });
      }
    }

    // Reset the input value
    if (input) {
      input.value = "";
    }
  }

  remove(tag: Tag): void {
    const index = this.output.tags.indexOf(tag);

    if (index >= 0) {
      this.output.tags.splice(index, 1);
    }
  }

  ngOnInit() {}
  close() {
    this.dialogRef.close();
  }
  isOk(){
    if(this.output.transaction.title.length > 0 && this.output.transaction.amount != 0){
      return true;
    }
    return false;
  }
  onSubmit() {
    this.output.tags.forEach(tag => {
      this.output.transaction.tagIds.push(tag.title);
    });
    this.dialogRef.close(this.output);
  }
}
