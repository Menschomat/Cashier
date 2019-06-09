import { Component, OnInit, ViewChild } from "@angular/core";
import { Chart } from "chart.js";
import { Subscription } from "rxjs";
import { TagService } from "src/app/services/tag.service";
import { TransactionService } from "src/app/services/transaction.service";
import { StatusServiceService } from "src/app/services/status-service.service";

@Component({
  selector: "app-stack-bar-chart-card",
  templateUrl: "./stack-bar-chart-card.component.html",
  styleUrls: ["./stack-bar-chart-card.component.scss"]
})
export class StackBarChartCardComponent implements OnInit {
  @ViewChild("barChart") private chartRef;
  chart: any;
  subscription: Subscription;
  constructor(
    private tagService: TagService,
    private transService: TransactionService,
    private statusService: StatusServiceService
  ) {
    this.subscription = this.statusService.getMessage().subscribe(status => {
      if (status.saved) {
        this.renderChartData();
      }
    });
  }
  renderChartData() {
    this.transService.getLatesTransactions().subscribe(transs => {
      this.chart.data.datasets[0].data = [];
      this.chart.data.datasets[0].backgroundColor = [];
      this.chart.data.labels = [];
      let tagCountBuffer: any = {};
      transs.forEach(trans => {
        trans.tagIds.forEach(tID => {
          if (tagCountBuffer[tID]) {
            tagCountBuffer[tID] += trans.amount;
          } else {
            tagCountBuffer[tID] = trans.amount;
          }
        });
      });
      Object.keys(tagCountBuffer).forEach(tag => {
        this.chart.data.datasets[0].data.push(tagCountBuffer[tag]);
        this.chart.data.labels.push(`#${tag}`);
        this.chart.data.datasets[0].backgroundColor.push(
          this.tagService.getTag(tag).color
        );
      });
      this.chart.update();
    });
  }
  ngOnInit() {
    this.chart = new Chart(this.chartRef.nativeElement, {
      type: "doughnut",
      data: {
        datasets: [
          {
            data: [],
            backgroundColor: []
          }
        ],
        labels: []
      },
      options: {
        legend: {
          position: "right",
          display: true
        },
        tooltips: {
          callbacks: {
            label: function(tooltipItem, data) {
              var label = data.labels[tooltipItem.index] || "";

              if (label) {
                label += ": ";
              }
              label +=
                data.datasets[tooltipItem.datasetIndex].data[
                  tooltipItem.index
                ].toString() + "€";
              return label;
            }
          }
        }
      }
    });
    this.renderChartData();
  }
}
