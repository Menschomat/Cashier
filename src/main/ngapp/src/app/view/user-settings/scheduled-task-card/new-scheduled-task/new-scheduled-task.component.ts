import { Component, OnInit, ViewChild, ElementRef } from "@angular/core";
import {
  MatAutocomplete,
  MatAutocompleteSelectedEvent
} from "@angular/material/autocomplete";
import { MatDialogRef } from "@angular/material/dialog";
import { faTimes } from "@fortawesome/free-solid-svg-icons";
import { COMMA, ENTER, SPACE } from "@angular/cdk/keycodes";
import { MatChipInputEvent } from "@angular/material/chips";
import { Tag } from "src/app/model/hashtag-system/tag";
import { TagService } from "src/app/services/tag.service";
import { Observable } from "rxjs";
import { map, startWith } from "rxjs/operators";
import cronstrue from "cronstrue";
import {
  FormGroup,
  FormBuilder,
  Validators,
  FormControl
} from "@angular/forms";
import { ThemeService } from "src/app/services/theme.service";
import { ScheduledTask } from "src/app/model/transaction-management/scheduled-task";
import { NewScheduledTask } from "src/app/model/transaction-management/new-scheduled-task";
@Component({
  selector: "app-new-scheduled-task",
  templateUrl: "./new-scheduled-task.component.html",
  styleUrls: ["./new-scheduled-task.component.scss"]
})
export class NewScheduledTaskComponent implements OnInit {
  public newTransactionForm: FormGroup;
  output = {} as NewScheduledTask;
  allTags: Tag[] = [];
  filteredTags: Observable<string[]>;
  tagCtrl = new FormControl();
  @ViewChild("taginput", { static: true }) tagInput: ElementRef<
    HTMLInputElement
  >;
  @ViewChild("auto", { static: true }) matAutocomplete: MatAutocomplete;
  constructor(
    private dialogRef: MatDialogRef<NewScheduledTaskComponent>,
    private tagService: TagService,
    private fb: FormBuilder,
    private themeService: ThemeService
  ) {
    this.output.task = {} as ScheduledTask;
    this.output.tags = [];
    this.output.task.cronTab = "";
    this.output.task.tags = [];
    this.output.task.amount = 0;
    this.output.task.title = "";
    this.output.task.ingestion = false;
    this.allTags = this.tagService.allTags;
    this.filteredTags = this.tagCtrl.valueChanges.pipe(
      startWith(null),
      map((tagid: string | null) => (tagid ? this._filter(tagid) : []))
    );
  }

  faClose = faTimes;
  visible = true;
  selectable = true;
  removable = true;
  addOnBlur = true;
  readonly separatorKeysCodes: number[] = [ENTER, SPACE, COMMA];

  createForm() {
    this.newTransactionForm = this.fb.group({
      title: ["", Validators.required],
      amount: [0, Validators.pattern("^[-+]?[0-9]*.?[0-9]+$")],
      type: [Validators.required],
      cronTab: ["", Validators.required]
    });
  }
  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;
    this.addTag(value);
    if (input) {
      input.value = "";
    }
    this.clearAutocomplete();
  }
  private addTag(value: string) {
    const toAdd = value.replace("#", "");
    if ((toAdd || "").trim()) {
      let found = false;
      for (var i = 0; i < this.output.tags.length; i++) {
        if (
          this.output.tags[i].title.toLocaleLowerCase().trim() ===
          toAdd.toLocaleLowerCase().trim()
        ) {
          found = true;
          break;
        }
      }
      if (!found) {
        this.output.tags.push(
          this.tagService.getTag(toAdd.toLocaleLowerCase().trim())
        );
      }
    }
  }

  remove(tag: Tag): void {
    const index = this.output.tags.indexOf(tag);

    if (index >= 0) {
      this.output.tags.splice(index, 1);
    }
  }

  ngOnInit() {
    this.createForm();
  }
  close() {
    this.dialogRef.close();
  }
  onSubmit() {
    this.output.tags.map(tag => tag.title.toLowerCase().trim());
    this.output.task.tags = this.output.tags;
    this.output.task.amount = this.newTransactionForm.value.amount;
    this.output.task.title = this.newTransactionForm.value.title;
    this.output.task.cronTab = this.newTransactionForm.value.cronTab;
    this.output.task.ingestion = this.newTransactionForm.value.type;
    this.dialogRef.close(this.output);
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    this.addTag(event.option.viewValue);
    this.clearAutocomplete();
  }

  private clearAutocomplete() {
    this.tagInput.nativeElement.value = "";
    this.tagCtrl.setValue(null);
  }

  private _filter(value: string): string[] {
    let filterValue = value
      .toLocaleLowerCase()
      .trim()
      .replace("#", "");
    if (filterValue.length < 1) {
      return [];
    }
    return this.allTags
      .map(t => t.title)
      .filter(t => t.toLowerCase().indexOf(filterValue) === 0);
  }

  isCronValid(freq) {
    const cronregex = new RegExp(
      /^(\*|([0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9])|\*\/([0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9])) (\*|([0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9])|\*\/([0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9])) (\*|([0-9]|1[0-9]|2[0-3])|\*\/([0-9]|1[0-9]|2[0-3])) (\*|([1-9]|1[0-9]|2[0-9]|3[0-1])|\*\/([1-9]|1[0-9]|2[0-9]|3[0-1])) (\*|([1-9]|1[0-2])|\*\/([1-9]|1[0-2])) (\*|([0-6])|\*\/([0-6]))$/
    );
    return cronregex.test(freq);
  }

  cronToText(cron: string) {
    if (cron) {
      if (this.isCronValid(cron)) {
        return cronstrue.toString(cron, { use24HourTimeFormat: true });
      }
    }
    return "Not a crontab (* * * * * *)";
  }
}
