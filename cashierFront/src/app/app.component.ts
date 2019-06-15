import { Component, ChangeDetectorRef, ViewChild } from "@angular/core";
import { MediaMatcher } from '@angular/cdk/layout';
import { Subscription } from 'rxjs';
import { StatusServiceService } from './services/status-service.service';
import { MatSidenav } from '@angular/material';

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"]
})
export class AppComponent {
  darkMode: boolean;
  subscription: Subscription;
  constructor(changeDetectorRef: ChangeDetectorRef, media: MediaMatcher) {
    this.darkMode = localStorage.getItem("darkTheme") == "true";
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
  }
  title = "Cashier";
  darkModeChange(event) {
    this.darkMode = event;
  }
  mobileQuery: MediaQueryList;

  fillerNav = Array.from({length: 50}, (_, i) => `Nav Item ${i + 1}`);

  fillerContent = Array.from({length: 50}, () =>
      `Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
       labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
       laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in
       voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat
       cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.`);

  private _mobileQueryListener: () => void;

  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }
  isLoggedin() {
    return localStorage.getItem("cashierUserToken") ? true : false;
  }

}
