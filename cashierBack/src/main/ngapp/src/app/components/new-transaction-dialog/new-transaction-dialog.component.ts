import { Component, OnInit, ViewChild, ElementRef } from "@angular/core";
import { MatAutocomplete, MatAutocompleteSelectedEvent } from "@angular/material/autocomplete";
import { MatDialogRef } from "@angular/material/dialog";
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
  FormControl
} from "@angular/forms";
import { ThemeService } from "src/app/services/theme.service";
@Component({
  selector: "app-new-transaction-dialog",
  templateUrl: "./new-transaction-dialog.component.html",
  styleUrls: ["./new-transaction-dialog.component.scss"]
})
export class NewTransactionDialogComponent implements OnInit {
  public newTransactionForm: FormGroup;
  output = {} as NewTransaction;
  allTags: Tag[] = [];
  filteredTags: Observable<string[]>;
  tagCtrl = new FormControl();
  @ViewChild("taginput", { static: true }) tagInput: ElementRef<HTMLInputElement>;
  @ViewChild("auto", { static: true }) matAutocomplete: MatAutocomplete;
  constructor(
    private dialogRef: MatDialogRef<NewTransactionDialogComponent>,
    private tagService: TagService,
    private fb: FormBuilder,
    private themeService: ThemeService
  ) {
    this.output.tags = [];
    this.output.transaction = {} as Transaction;
    this.output.transaction.tags = [];
    this.output.transaction.amount = 0;
    this.output.transaction.title = "";
    this.output.transaction.date = "";
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
      amount: [0, Validators.pattern("^[+]?[0-9]*.?[0-9]+$")],
      type: ["", Validators.required],
      date: ["", Validators.required]
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
    let toAdd = value.replace("#", "");
    if ((toAdd || "").trim()) {
      var found = false;
      for (var i = 0; i < this.output.tags.length; i++) {
        if (
          this.output.tags[i].title.toLocaleLowerCase().trim() ==
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
    this.output.tags.forEach(tag => {

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
}
