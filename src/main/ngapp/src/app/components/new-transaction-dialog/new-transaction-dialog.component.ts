import {
  Component,
  OnInit,
  ViewChild,
  ElementRef,
  Inject,
} from "@angular/core";
import {
  MatAutocomplete,
  MatAutocompleteSelectedEvent,
} from "@angular/material/autocomplete";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { faTimes } from "@fortawesome/free-solid-svg-icons";
import { COMMA, ENTER, SPACE } from "@angular/cdk/keycodes";
import { MatChipInputEvent } from "@angular/material/chips";
import { Tag } from "src/app/model/hashtag-system/tag";
import { TagService } from "src/app/services/tag.service";
import { NewTransaction } from "src/app/model/transaction-management/new-transaction";
import { Transaction } from "src/app/model/transaction-management/transaction";
import { Observable } from "rxjs";
import { map, startWith } from "rxjs/operators";
import {
  FormGroup,
  FormBuilder,
  Validators,
  FormControl,
} from "@angular/forms";
import { DatePipe } from "@angular/common";
@Component({
  selector: "app-new-transaction-dialog",
  templateUrl: "./new-transaction-dialog.component.html",
  styleUrls: ["./new-transaction-dialog.component.scss"],
})
export class NewTransactionDialogComponent implements OnInit {
  public newTransactionForm: FormGroup;
  output = {} as NewTransaction;
  allTags: Tag[] = [];
  filteredTags: Observable<string[]>;
  tagCtrl = new FormControl();
  @ViewChild("taginput", { static: true }) tagInput: ElementRef<
    HTMLInputElement
  >;
  @ViewChild("auto", { static: true }) matAutocomplete: MatAutocomplete;
  constructor(
    private dialogRef: MatDialogRef<NewTransactionDialogComponent>,
    private tagService: TagService,
    private fb: FormBuilder,
    private pipe: DatePipe,
    @Inject(MAT_DIALOG_DATA) public data
  ) {
    if (data.data) {
      this.output.transaction = data.data;
      this.output.tags = JSON.parse(JSON.stringify(data.data.tags));
    } else {
      this.output.tags = [];
      this.output.transaction = {} as Transaction;
      this.output.transaction.tags = [];
      this.output.transaction.amount = 0;
      this.output.transaction.title = "";
      this.output.transaction.date = "";
    }

    this.allTags = this.tagService.allTags;
    this.filteredTags = this.tagCtrl.valueChanges.pipe(
      startWith(null as any),
      map((tagid: string | null) => (tagid ? this._filter(tagid) : []))
    );
  }

  faClose = faTimes;
  visible = true;
  selectable = true;
  removable = true;
  addOnBlur = true;
  readonly separatorKeysCodes: number[] = [SPACE, COMMA];

  createForm(pre: Transaction) {
    if (pre) {
      this.newTransactionForm = this.fb.group({
        title: [pre.title, Validators.required],
        amount: [pre.amount, Validators.pattern("^[+]?[0-9]*.?[0-9]+$")],
        type: [pre.ingestion, Validators.required],
        date: [
          new Date(this.pipe.transform(pre.date, "fullDate")),
          Validators.required,
        ],
      });
    } else {
      this.newTransactionForm = this.fb.group({
        title: ["", Validators.required],
        amount: [0, Validators.pattern("^[+]?[0-9]*.?[0-9]+$")],
        type: ["", Validators.required],
        date: ["", Validators.required],
      });
    }
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
      for (const tag of this.output.tags) {
        if (
          tag.title.toLocaleLowerCase().trim() ===
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
    this.createForm(this.data.data);
  }
  close() {
    this.dialogRef.close();
  }
  onSubmit() {
    this.output.tags.forEach((tag) => {
      tag.title = tag.title.toLocaleLowerCase().trim();
    });
    this.output.transaction.tags = this.output.tags;
    this.output.transaction.ingestion = this.newTransactionForm.value.type;
    this.output.transaction.amount = this.newTransactionForm.value.amount;
    this.output.transaction.title = this.newTransactionForm.value.title;
    this.output.transaction.date = this.newTransactionForm.value.date;
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
    const filterValue = value.toLocaleLowerCase().trim().replace("#", "");
    if (filterValue.length < 1) {
      return [];
    }
    return this.allTags
      .map((t) => t.title)
      .filter((t) => t.toLowerCase().indexOf(filterValue) === 0);
  }
}
