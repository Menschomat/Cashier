import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { StatusServiceService } from 'src/app/services/status-service.service';
import { UserService } from 'src/app/services/user.service';
import { faChartLine, faTable, faHome, faCog, faUserCog, faMoneyBillWave, faSignOutAlt, faUserCircle, faMoon, faSun } from '@fortawesome/free-solid-svg-icons';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-sidebar-content',
  templateUrl: './sidebar-content.component.html',
  styleUrls: ['./sidebar-content.component.scss']
})
export class SidebarContentComponent implements OnInit {

  @Output() darkOut: EventEmitter<boolean> = new EventEmitter<boolean>();
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
  switchDark() {
    this.dark = !this.dark;
    localStorage.setItem("darkTheme", this.dark.toString());
    this.darkOut.emit(this.dark);
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
}
