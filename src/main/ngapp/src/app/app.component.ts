import { Component, HostBinding, ChangeDetectorRef } from "@angular/core";
import { OverlayContainer } from "@angular/cdk/overlay";
import { Subscription } from "rxjs";
import { MediaMatcher } from "@angular/cdk/layout";
import { ThemeService } from "./services/theme.service";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"]
})
export class AppComponent {
  @HostBinding("class") componentCssClass;
  darkMode: boolean;
  title = "Cashier";
  constructor(
    public overlayContainer: OverlayContainer,
    changeDetectorRef: ChangeDetectorRef,
    media: MediaMatcher,
    private themeService: ThemeService
  ) {
    this.darkMode = this.getCurrentTheme() === "dark";
    this.onSetTheme(this.darkMode);
  }

  onSetTheme(dark) {
    this.darkMode = dark;
    if (this.darkMode) {
      this.cangeTheme("mat-app-dark", "mat-app-light");
    } else {
      this.cangeTheme("mat-app-light", "mat-app-dark");
    }
  }

  cangeTheme(theme: string, prev: string) {
    if (prev) {
      this.overlayContainer.getContainerElement().classList.remove(prev);
    }
    this.overlayContainer.getContainerElement().classList.add(theme);
    this.componentCssClass = theme;
  }


  getCurrentTheme() {
    return this.themeService.getTheme();
  }

  isLoggedin() {
    return localStorage.getItem("cashierUserToken") ? true : false;
  }
}
