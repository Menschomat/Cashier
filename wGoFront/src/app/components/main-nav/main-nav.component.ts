import { Component, OnInit } from "@angular/core";
import {
  faTrafficLight,
  faUserCircle,
  faSignOutAlt,
  faMoneyBillWaveAlt,
  faMoneyBillWave
} from "@fortawesome/free-solid-svg-icons";
import { Subscription } from "rxjs";
import { StatusServiceService } from "src/app/services/status-service.service";
import { UserService } from "src/app/services/user.service";

@Component({
  selector: "app-main-nav",
  templateUrl: "./main-nav.component.html",
  styleUrls: ["./main-nav.component.scss"]
})
export class MainNavComponent implements OnInit {
  faUser = faUserCircle;
  faSignout = faSignOutAlt;
  faMoney = faMoneyBillWave;
  subscription: Subscription;
  userstring: String;
  constructor(
    private statusService: StatusServiceService,
    private userService: UserService
  ) {
    this.subscription = this.statusService.getMessage().subscribe(status => {
      if (status.loggedIn) {
        this.initUserString();
      } else if (status.loggedIn == false) {
        this.userstring = "";
      }
    });
  }
  isLoggedin() {
    return localStorage.getItem("cashierUserToken") ? true : false;
  }
  initUserString() {
    this.userService.getUser().subscribe(user => {
      if (user.name && user.surname) {
        this.userstring = `${user.name.charAt(0)}. ${user.surname}`;
      } else {
        this.userstring = user.username;
      }
    });
  }
  ngOnInit() {
    this.initUserString();
  }
}
