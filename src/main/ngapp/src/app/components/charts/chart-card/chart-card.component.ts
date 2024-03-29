import {
  Component,
  OnInit,
  ViewChild,
  Input,
  SimpleChange, OnChanges
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
    const tagCountBuffer: any = {};
    if (this.data.length > 0) {
      this.data.forEach(trans => {
        if (!trans.ingestion)
          trans.tags.forEach(tID => {
            if (tagCountBuffer[tID.title]) {
              tagCountBuffer[tID.title] += trans.amount;
            } else {
              tagCountBuffer[tID.title] = trans.amount;
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
        maintainAspectRatio: false,
        responsive: true,
        legend: {
          position: "right",
          display: true
        },
        /*        tooltips: {
                  callbacks: {
                    label: function(tooltipItem, data) {
                      let label =  data.labels[tooltipItem.index] as string || "";

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
                }*/
      }
    });
    this.renderChartData();
  }
  ngOnChanges(changes: SimpleChange) {
    if (this.data && this.chart) {
      this.renderChartData();
    }
  }
}
