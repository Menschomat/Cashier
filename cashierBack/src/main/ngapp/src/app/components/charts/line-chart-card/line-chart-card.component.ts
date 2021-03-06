import {
  Component,
  OnInit,
  ViewChild,
  Input,
  SimpleChange,
} from "@angular/core";
import { Chart } from "chart.js";
import { Subscription } from "rxjs";
import { Transaction } from "src/app/model/transaction-management/transaction";
import { ThemeService } from "src/app/services/theme.service";

@Component({
  selector: "line-chart-card",
  templateUrl: "./line-chart-card.component.html",
  styleUrls: ["./line-chart-card.component.scss"],
})
export class StackBarChartCardComponent implements OnInit {
  @Input()
  data: Transaction[] = [];

  @ViewChild("lineChart", { static: true }) private chartRef;
  chart: any;
  subscription: Subscription;
  constructor(private themeService: ThemeService) {}
  renderChartData() {

    this.chart.data.datasets[0].data = [];
    this.chart.data.labels = [];
    let tracker: number = 0;
    if (this.data.length > 0) {
      this.data.sort(function (a, b) {
        var c = Date.parse(
          a.date.split("+")[0].substring(0, a.date.split("+")[0].length - 4)
        );
        var d = Date.parse(
          b.date.split("+")[0].substring(0, b.date.split("+")[0].length - 4)
        );
        return c - d;
      });
      this.data.forEach((trans) => {
        this.chart.data.labels.push(trans.date);
        if (trans.ingestion) {
          tracker = tracker + trans.amount;
          this.chart.data.datasets[0].data.push({
            x: trans.date,
            y: tracker,
          });
        } else {
          tracker = tracker - trans.amount;
          this.chart.data.datasets[0].data.push({
            x: trans.date,
            y: tracker,
          });
        }
      });
    }

    this.chart.update();
  }
  ngOnInit() {
    this.chart = new Chart(this.chartRef.nativeElement, {
      type: "line",
      data: {
        labels: [],
        datasets: [
          {
            data: [],
            label: "Trend",
            borderColor:
              this.themeService.getTheme() === "dark" ? "white" : "purple",
            fill: true,
            backgroundColor: "rgba(141, 36, 170, 0.2)",
            lineTension: 0.15,
          },
        ],
      },
      options: {
        maintainAspectRatio: false,
        responsive: true,
        scales: {
          xAxes: [
            {
              type: "time",
            },
          ],
        },
      },
    });
    this.renderChartData();
  }
  ngOnChanges(changes: SimpleChange) {
    if (this.data && this.chart) {
      this.renderChartData();
    }
  }
}
