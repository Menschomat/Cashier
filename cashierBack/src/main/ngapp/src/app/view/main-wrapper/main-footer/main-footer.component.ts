import { Component, OnInit } from "@angular/core";
import { AppConfig } from "../../../app-config";
@Component({
  selector: "app-main-footer",
  templateUrl: "./main-footer.component.html",
  styleUrls: ["./main-footer.component.scss"]
})
export class MainFooterComponent implements OnInit {
  version: string = AppConfig.VERSION;
  constructor() {}

  ngOnInit() {}
}
