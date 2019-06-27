import { Component, OnInit, Input, ViewChild, SimpleChange } from '@angular/core';
import { Transaction } from 'src/app/model/transaction-management/transaction';
import { Subscription } from 'rxjs';
import { TagService } from 'src/app/services/tag.service';
import { Chart } from "chart.js";

@Component({
  selector: 'app-bar-chart-card',
  templateUrl: './bar-chart-card.component.html',
  styleUrls: ['./bar-chart-card.component.scss']
})
export class BarChartCardComponent implements OnInit {

  @Input()
  data: Transaction[] = [];
  @ViewChild("doughChart", { static: true }) private chartRef;
  chart: any;
  subscription: Subscription;
  constructor(private tagService: TagService) {}
  renderChartData() {
    this.chart.data.datasets[0].data = [];
    this.chart.data.datasets[0].backgroundColor = [];
    this.chart.data.labels = [];
    let issueBuffer: any = {};
    if (this.data.length > 0) {
      this.data.forEach(trans => {
        if (!trans.ingestion)
          trans.tags.forEach(tID => {
            if (issueBuffer[tID.title]) {
              issueBuffer[tID.title] += trans.amount;
            } else {
              issueBuffer[tID.title] = trans.amount;
            }
          });
      });
      Object.keys(issueBuffer).forEach(tag => {
        this.chart.data.datasets[0].data.push(issueBuffer[tag]);
        this.chart.data.labels.push(`#${tag}`);
        this.chart.data.datasets[0].backgroundColor.push(
          this.tagService.getTag(tag).color
        );
      });
    }
    this.chart.update();
  }
  ngOnInit() {
    this.chart = new Chart(this.chartRef.nativeElement, {
      type: 'horizontalBar',
      data: {
        labels: [],
        datasets: [
          {
            label: "",
            backgroundColor: [],
            data: []
          }
        ]
      },
      options: {
        legend: { display: false },
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
  }
  ngOnChanges(changes: SimpleChange) {
    if (this.data && this.chart) {
      this.renderChartData();
    }
  }

}
