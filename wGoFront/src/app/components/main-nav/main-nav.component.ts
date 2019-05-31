import { Component, OnInit } from "@angular/core";
import { faTrafficLight } from "@fortawesome/free-solid-svg-icons";
import { Subscription } from "rxjs";
import { TagService } from "src/app/services/tag.service";
import { StatusServiceService } from "src/app/services/status-service.service";
import { Status } from 'src/app/model/status';

@Component({
  selector: "app-main-nav",
  templateUrl: "./main-nav.component.html",
  styleUrls: ["./main-nav.component.scss"]
})
export class MainNavComponent implements OnInit {
  faTraffic = faTrafficLight;
  status: Status = {} as Status;
  subscription: Subscription;
  constructor(private statusService: StatusServiceService) {
    this.status.saved = true;
    this.subscription = this.statusService.getMessage().subscribe(status => {
      this.status = status;
    });
  }

  ngOnInit() {}
}
