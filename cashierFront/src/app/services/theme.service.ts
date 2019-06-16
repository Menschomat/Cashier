import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {
  private theme: string = "light";

  constructor() {
    if (localStorage.getItem("theme")) {
      this.theme = localStorage.getItem("theme");
    }
  }

  public getTheme(): string {
    return this.theme;
  }
  public setTheme(newTheme: string):string {
    if (newTheme === "light" || newTheme === "dark") {
      console.log("ThemeChange", newTheme);
      
      this.theme = newTheme;
      localStorage.setItem("theme", newTheme);
    }
    return this.theme;
  }
}
