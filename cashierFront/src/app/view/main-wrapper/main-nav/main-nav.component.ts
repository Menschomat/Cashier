import { Component, OnInit, Output, EventEmitter } from "@angular/core";
import {
  faUserCircle,
  faSignOutAlt,
  faMoneyBillWave,
  faUserCog,
  faCogs,
  faCog,
  faHome,
  faTable,
  faChartLine,
  faMoon,
  faSun
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
  @Output() darkOut: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Output() toggleSide: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Output() logoutEvent: EventEmitter<boolean> = new EventEmitter<boolean>();
  dark: boolean = false;
  faUser = faUserCircle;
  faSignout = faSignOutAlt;
  faMoney = faMoneyBillWave;
  faUserCog = faUserCog;
  faCog = faCog;
  faHome = faHome;
  faTable = faTable;
  faChart = faChartLine;
  faMoon = faMoon;
  faSun = faSun;
  subscription: Subscription;
  userstring: String;
  user: any;
  constructor(
    private statusService: StatusServiceService,
    private userService: UserService,
  ) {
    this.subscription = this.statusService.getMessage().subscribe(status => {
      if (status.loggedIn) {
        this.initUserString();
      } else if (status.loggedIn == false) {
        this.userstring = "";
      }
    });
  }
  switchDark() {
    this.dark = !this.dark;
    localStorage.setItem("darkTheme", this.dark.toString());
    this.darkOut.emit(this.dark);
  }
  toggleSideBar(){
    this.toggleSide.emit(true);
  }
  isLoggedin() {
    return localStorage.getItem("cashierUserToken") ? true : false;
  }
  initUserString() {
    this.userService.getUser().subscribe(user => {
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
    this.dark = localStorage.getItem("darkTheme") == "true";
    this.darkOut.emit(this.dark);
    if (localStorage.getItem("cashierUserToken")) {
      this.initUserString();
    }
  }
  logout(){
    this.logoutEvent.emit();
  }
}