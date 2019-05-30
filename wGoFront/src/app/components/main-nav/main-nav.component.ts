import { Component, OnInit } from '@angular/core';
import { faTrafficLight } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-main-nav',
  templateUrl: './main-nav.component.html',
  styleUrls: ['./main-nav.component.scss']
})
export class MainNavComponent implements OnInit {
  faTraffic = faTrafficLight;
  constructor() { }

  ngOnInit() {
  }

}
