import { Component, OnInit } from '@angular/core';
import {
  FormGroup,
  Validators,
  FormBuilder
} from "@angular/forms";
import { RegistrationValidator } from "src/app/auth/helpers/register.validator";
import { UserService } from "src/app/services/user.service";
import { HttpErrorResponse } from "@angular/common/http";
import { MatSnackBar } from '@angular/material';
import { Router } from '@angular/router';
@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {

  public newPasswordForm: FormGroup;
  changeNotPossible: boolean = false;

  constructor(
    private newPasswordFormBuilder: FormBuilder,
    private userService: UserService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {}
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
  onPWChange() {
    if (this.newPasswordForm.valid) {
      this.userService
        .changePassword(
          this.newPasswordForm.value.currentPW,
          this.newPasswordForm.value.password
        )
        .subscribe(
          repsonse => {
            this.openSnackBar("Passwort geändert!","Close","success-dialog");
            this.router.navigate(['/login']);
          },
          (error: HttpErrorResponse) => {
            this.createForm();
            this.openSnackBar("Current Passwort möglicherweise falsch!","Close","error-dialog");
          }
        );
    }
  }
  openSnackBar(message: string, action: string, panelclass:string) {
    this.snackBar.open(message, action, {
      duration: 10000,
      verticalPosition: 'top',
      panelClass: [panelclass]
    });
}
}
