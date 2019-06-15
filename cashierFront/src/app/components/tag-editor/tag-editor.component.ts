import { Component, OnInit, Inject } from "@angular/core";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material";
import { Tag } from "src/app/model/tag";
import { faTimes } from "@fortawesome/free-solid-svg-icons";
import { TagService } from "src/app/services/tag.service";
import { StatusServiceService } from 'src/app/services/status-service.service';

@Component({
  selector: "app-tag-editor",
  templateUrl: "./tag-editor.component.html",
  styleUrls: ["./tag-editor.component.scss"]
})
export class TagEditorComponent implements OnInit {
  faClose = faTimes;
  colorPreset:string[] = [];
  constructor(
    @Inject(MAT_DIALOG_DATA) public tag: Tag,
    private dialogRef: MatDialogRef<TagEditorComponent>,
    private tagService: TagService,
    private statusService:StatusServiceService 
  ) {}

  ngOnInit() {
    this.colorPreset = this.tagService.getColorPreset();
  }
  close() {
    this.dialogRef.close();
  }
  selectColor(event:any){
    this.statusService.sendMessage({saved:false});
    this.tag.color=event;
    this.tagService.saveAndUpdateTagList();
    this.dialogRef.close();
  }
}
