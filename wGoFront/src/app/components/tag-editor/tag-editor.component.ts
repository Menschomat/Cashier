import { Component, OnInit, Inject } from "@angular/core";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material";
import { Tag } from "src/app/model/tag";
import { faTimes } from "@fortawesome/free-solid-svg-icons";
import { TagService } from "src/app/services/tag.service";

@Component({
  selector: "app-tag-editor",
  templateUrl: "./tag-editor.component.html",
  styleUrls: ["./tag-editor.component.scss"]
})
export class TagEditorComponent implements OnInit {
  faClose = faTimes;
  constructor(
    @Inject(MAT_DIALOG_DATA) public tag: Tag,
    private dialogRef: MatDialogRef<TagEditorComponent>,
    private tagService: TagService
  ) {}

  ngOnInit() {}
  close() {
    this.dialogRef.close();
  }
}
