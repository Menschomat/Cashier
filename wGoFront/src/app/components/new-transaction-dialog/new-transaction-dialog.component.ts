import { Component, OnInit } from "@angular/core";
import { MatDialogRef } from "@angular/material";
import { faTimes } from "@fortawesome/free-solid-svg-icons";
import { COMMA, ENTER, SPACE, SEMICOLON } from "@angular/cdk/keycodes";
import { MatChipInputEvent } from "@angular/material";
import { Tag } from "src/app/model/tag";
import { TagService } from 'src/app/services/tag.service';
@Component({
  selector: "app-new-transaction-dialog",
  templateUrl: "./new-transaction-dialog.component.html",
  styleUrls: ["./new-transaction-dialog.component.scss"]
})
export class NewTransactionDialogComponent implements OnInit {
  constructor(private dialogRef: MatDialogRef<NewTransactionDialogComponent>,private tagService: TagService) {}

  faClose = faTimes;
  visible = true;
  selectable = true;
  removable = true;
  addOnBlur = true;
  readonly separatorKeysCodes: number[] = [ENTER,SPACE,COMMA,SEMICOLON];
  tags: Tag[] = [];

  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    // Add our fruit
    if ((value || "").trim()) {
      this.tags.push({ title: value.trim(), color: this.tagService.getColor() });
    }

    // Reset the input value
    if (input) {
      input.value = "";
    }
  }

  remove(tag: Tag): void {
    const index = this.tags.indexOf(tag);

    if (index >= 0) {
      this.tags.splice(index, 1);
    }
  }

  ngOnInit() {}
  close() {
    this.dialogRef.close();
  }
}
