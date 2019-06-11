import { Component, OnInit } from "@angular/core";
import { MatDialogRef } from "@angular/material";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { faTimes } from "@fortawesome/free-solid-svg-icons";
import { DbUser } from "src/app/model/db-user";
import { RegistrationValidator } from "src/app/helpers/register.validator";

@Component({
  selector: "app-user-edit-dialog",
  templateUrl: "./user-edit-dialog.component.html",
  styleUrls: ["./user-edit-dialog.component.scss"]
})
export class UserEditDialogComponent implements OnInit {
  public newUserForm: FormGroup;
  faClose = faTimes;
  outputUser: DbUser = {} as DbUser;
  constructor(
    private dialogRef: MatDialogRef<UserEditDialogComponent>,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.createForm();
  }
  close() {
    this.dialogRef.close();
  }
  createForm() {
    this.newUserForm = this.fb.group(
      {
        uname: ["", [Validators.required, Validators.minLength(4)]],
        password: ["", [Validators.required, Validators.minLength(6)]],
        repeatPassword: ["", Validators.required],
        name: [""],
        surname: [""],
        date: [""],
        role: ["", Validators.required]
      },
      {
        validator: RegistrationValidator.validate.bind(this)
      }
    );
  }
  onSubmit() {
    this.outputUser.dateOfBirth = this.newUserForm.value.date;
    this.outputUser.password = this.newUserForm.value.password;
    this.outputUser.username = this.newUserForm.value.uname;
    this.outputUser.name = this.newUserForm.value.name;
    this.outputUser.surname = this.newUserForm.value.surname;
    this.outputUser.role = this.newUserForm.value.role;
    this.dialogRef.close(this.outputUser);
  }
}
