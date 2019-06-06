import { Component, OnInit } from "@angular/core";
import {
  faTrafficLight,
  faUserCircle,
  faSignOutAlt
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
  subscription: Subscription;
  currentUser: String;
  constructor(
    private statusService: StatusServiceService,
    private userService: UserService
  ) {
    this.subscription = this.statusService.getMessage().subscribe(status => {
      if (status.loggedIn) {
        this.userService.getUser().subscribe(user => {
          this.currentUser = user.username;
        });
      } else if (status.loggedIn == false) {
        this.currentUser = "";
      }
    });
  }
  isLoggedin() {
    return localStorage.getItem("cashierUserToken") ? true : false;
  }

  ngOnInit() {
    this.userService.getUser().subscribe(user => {
      this.currentUser = user.username;
    });
  }
}
