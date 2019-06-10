import { Component, OnInit } from "@angular/core";
import {
  faUserCircle,
  faSignOutAlt,
  faMoneyBillWave,
  faUserCog,
  faCogs,
  faCog
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
  faUserCog = faUserCog;
  faCog = faCog;
  subscription: Subscription;
  userstring: String;
  user: any;
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
      console.log(user);

      this.user = user;
      if (user.name && user.surname) {
        this.userstring = `${user.name.charAt(0)}. ${user.surname}`;
      } else {
        this.userstring = user.username;
      }
    });
  }
  userIsAdmin(user: any) {
    if (user && user.role === "ADMIN") {
      return true;
    }
    return false;
  }
  ngOnInit() {
    this.initUserString();
  }
}
