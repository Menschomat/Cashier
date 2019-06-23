import { Component, OnInit } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { UserService } from "src/app/services/user.service";
import { FrontendUser } from "src/app/model/user-management/frontend-user";
import { Router } from "@angular/router";
import { StatusServiceService } from "src/app/services/status-service.service";

@Component({
  selector: "app-init",
  templateUrl: "./init.component.html",
  styleUrls: ["./init.component.scss"]
})
export class InitComponent implements OnInit {
  isLinear = false;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  user: FrontendUser = {} as FrontendUser;
  constructor(
    private _formBuilder: FormBuilder,
    private userService: UserService,
    private statusService: StatusServiceService,
    private router: Router
  ) {}

  ngOnInit() {
    this.userService.getUser().subscribe((user: FrontendUser) => {
      this.user = user;
      this.initForm();
    });
    this.initForm();
  }
  initForm() {
    this.firstFormGroup = this._formBuilder.group({
      name: [this.user.name, [Validators.required, Validators.minLength(2)]],
      surname: [
        this.user.surname,
        [Validators.required, Validators.minLength(3)]
      ]
    });
    this.secondFormGroup = this._formBuilder.group({
      email: [this.user.email, [Validators.required, Validators.email]]
    });
  }
  initUser() {
    if (this.firstFormGroup.valid && this.secondFormGroup.valid) {
      this.user.name = this.firstFormGroup.value.name;
      this.user.surname = this.firstFormGroup.value.surname;
      this.user.email = this.secondFormGroup.value.email;
      this.user.initialized = true;
      this.userService.updateUser(this.user).subscribe(user => {
        this.statusService.sendMessage({ loggedIn: true });
        this.router.navigate(["/"]);
      });
    }
  }
}
