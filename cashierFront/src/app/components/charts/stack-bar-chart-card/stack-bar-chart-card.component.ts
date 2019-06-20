import { Component, OnInit, ViewChild, Input, SimpleChange } from "@angular/core";
import { Chart } from "chart.js";
import { Subscription } from "rxjs";
import { TagService } from "src/app/services/tag.service";
import { TransactionService } from "src/app/services/transaction.service";
import { StatusServiceService } from "src/app/services/status-service.service";
import { Transaction } from 'src/app/model/transaction-management/transaction';
import * as moment from 'moment';

@Component({
  selector: "app-stack-bar-chart-card",
  templateUrl: "./stack-bar-chart-card.component.html",
  styleUrls: ["./stack-bar-chart-card.component.scss"]
})
export class StackBarChartCardComponent implements OnInit {

  @Input()
  data: Transaction[] = [];

  @ViewChild("lineChart") private chartRef;
  chart: any;
  subscription: Subscription;
  constructor(private tagService: TagService) {}
  renderChartData() {
    this.chart.data.datasets[0].data = [];
    this.chart.data.datasets[0].backgroundColor = [];
    this.chart.data.labels = [];
    let tracker:number = 0;
    if (this.data.length > 0) {
    this.data.sort(function(a,b){    
      var c = Date.parse(a.date.split("+")[0].substring(0, a.date.split("+")[0].length - 4));
      var d = Date.parse(b.date.split("+")[0].substring(0, b.date.split("+")[0].length - 4));
      return c-d;
      });
      this.data.forEach(trans => {
        this.chart.data.labels.push(trans.date);
        if (trans.ingestion){
          tracker = tracker + trans.amount;
          this.chart.data.datasets[0].data.push({
            x: trans.date,
            y: tracker
          });
        }else{
          tracker = tracker - trans.amount;
          this.chart.data.datasets[0].data.push({
            x: trans.date,
            y: tracker
          });
        }
        console.log(trans.date,tracker);
        
      });
    }
 
    this.chart.update();
  }
  ngOnInit() {
    this.chart = new Chart(this.chartRef.nativeElement, {
      type: 'line',
      data: {
        labels: [],
        datasets: [{ 
            data: [],
            label: "Trend",
            borderColor: "purple",
            fill: false
          }
        ]
      },
      options: {
        scales: {
          xAxes: [{
            type: 'time'
          }]
        }
      }
    });
  }
  ngOnChanges(changes: SimpleChange) {
    if (this.data && this.chart) {
      this.renderChartData();
    }
  }
}
