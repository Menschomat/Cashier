import { Component, OnInit, Output, EventEmitter } from "@angular/core";
import {
  faUserCircle,
  faSignOutAlt,
  faMoneyBillWave,
  faUserCog,
  faCog,
  faHome,
  faTable,
  faChartLine,
  faMoon,
  faSun,
  faBars
} from "@fortawesome/free-solid-svg-icons";
import { Subscription } from "rxjs";
import { StatusServiceService } from "src/app/services/status-service.service";
import { UserService } from "src/app/services/user.service";
import { ThemeService } from "src/app/services/theme.service";
import { FrontendUser } from "src/app/model/user-management/frontend-user";
import { trigger, transition, style, animate } from "@angular/animations";

@Component({
  selector: "app-main-nav",
  animations: [
    trigger("enterAnimation", [
      transition(":enter", [
        style({ opacity: 0 }),
        animate("200ms", style({ opacity: 1 }))
      ]),
      transition(":leave", [
        style({ opacity: 1 }),
        animate("200ms", style({ opacity: 0 }))
      ])
    ])
  ],
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
  faBars = faBars;
  subscription: Subscription;
  userstring: String;
  user: FrontendUser = {} as FrontendUser;
  theme: string;
  constructor(
    private statusService: StatusServiceService,
    private userService: UserService,
    private themeService: ThemeService
  ) {
    this.user.email = "";
    this.theme = this.themeService.getTheme();
    this.subscription = this.statusService.getMessage().subscribe(status => {
      if (status.loggedIn) {
        this.initUserString();
      } else if (status.loggedIn == false) {
        this.userstring = "";
      }
    });
  }
  switchDark() {
    if (this.theme === "light") {
      this.theme = this.themeService.setTheme("dark");
    } else if (this.theme === "dark") {
      this.theme = this.themeService.setTheme("light");
    }
  }
  toggleSideBar() {
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
  logout() {
    this.logoutEvent.emit();
  }
}
