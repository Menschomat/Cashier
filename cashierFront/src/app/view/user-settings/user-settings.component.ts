import { Component, OnInit } from "@angular/core";
import {
  FormGroup,
  Validators,
  FormBuilder,
  ValidatorFn,
  AbstractControl
} from "@angular/forms";
import { RegistrationValidator } from "src/app/helpers/register.validator";

@Component({
  selector: "app-user-settings",
  templateUrl: "./user-settings.component.html",
  styleUrls: ["./user-settings.component.scss"]
})
export class UserSettingsComponent implements OnInit {
  public newPasswordForm: FormGroup;

  constructor(private newPasswordFormBuilder: FormBuilder) {}
  createForm() {
    this.newPasswordForm = this.newPasswordFormBuilder.group(
      {
        currentPW: ["", [Validators.required, Validators.minLength(6)]],
        password: ["", [Validators.required, Validators.minLength(6)]],
        repeatPassword: ["", [Validators.required]]
      },
      {
        validator: RegistrationValidator.validate.bind(this)
      }
    );
  }
  ngOnInit() {
    this.createForm();
  }
  onPWChange() {}
}
