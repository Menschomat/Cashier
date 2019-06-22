import {
  Component,
  OnInit,
  ViewChild,
  Input,
  SimpleChange
} from "@angular/core";
import { Chart } from "chart.js";
import { TagService } from "src/app/services/tag.service";
import { Subscription } from "rxjs";
import { Transaction } from "src/app/model/transaction-management/transaction";

@Component({
  selector: "app-chart-card",
  templateUrl: "./chart-card.component.html",
  styleUrls: ["./chart-card.component.scss"]
})
export class ChartCardComponent implements OnInit {
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
    let tagCountBuffer: any = {};
    if (this.data.length > 0) {
      this.data.forEach(trans => {
        if (!trans.ingestion)
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
    }
    this.chart.update();
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
  }
  ngOnChanges(changes: SimpleChange) {
    if (this.data && this.chart) {
      this.renderChartData();
    }
  }
}
