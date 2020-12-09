import { Component, Input, OnInit } from '@angular/core';
import { ChartDataSets, ChartOptions, ChartType } from 'chart.js';
import { Label, Color } from 'ng2-charts';

@Component({
  selector: 'jhi-bar-chart',
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.scss'],
})
export class BarChartComponent implements OnInit {
  public barChartOptions: ChartOptions = {
    responsive: true,
    // We use these empty structures as placeholders for dynamic theming.
    scales: { xAxes: [{}], yAxes: [{}] },
    plugins: {
      datalabels: {
        anchor: 'end',
        align: 'end',
      },
    },
  };
  @Input() barChartLabels: Label[] = [];
  @Input() width = 400;
  public barChartType: ChartType = 'bar';
  public barChartLegend = true;
  public barChartPlugins = [];

  @Input() barChartData: ChartDataSets[] = [];
  public ChartColors: Color[] = [
    {
      // orange
      backgroundColor: '#fd7500',
    },
    {
      // green
      backgroundColor: 'rgba(57,228,0,1)',
    },
    {
      // brown
      backgroundColor: 'rgba(106,72,40,1)',
    },
    {
      // accent (blue)
      backgroundColor: 'rgba(58,80,218,1)',
    },
  ];
  constructor() {}

  ngOnInit(): void {}
}
