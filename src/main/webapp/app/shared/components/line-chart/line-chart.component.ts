import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { ChartDataSets, ChartType, ChartOptions } from 'chart.js';
import { BaseChartDirective, Color, Label } from 'ng2-charts';

@Component({
  selector: 'jhi-line-chart',
  templateUrl: './line-chart.component.html',
  styleUrls: ['./line-chart.component.scss'],
})
export class LineChartComponent implements OnInit {
  @Input() width = 400;
  @Input() ChartData: ChartDataSets[] = [
    { data: [65, 59, 80, 81, 56, 55, 40], label: 'Series A' },
    { data: [28, 48, 40, 19, 86, 27, 90], label: 'Series B' },
    { data: [18, 48, 77, 9, 100, 27, 40], label: 'Series C' },
    { data: [10, 20, 30, 40, 50, 60, 70, 80], label: 'likes' },
  ];
  mode: ChartType = 'line';

  @Input() ChartLabels: Label[] = ['January', 'February', 'March', 'April', 'May', 'June', 'July'];

  public ChartOptions: ChartOptions & { annotation: any } = {
    responsive: true,
    scales: {
      // We use this empty structure as a placeholder for dynamic theming.
      xAxes: [{}],
      yAxes: [
        {
          id: 'y-axis-0',
          position: 'left',
        },
        {
          id: 'y-axis-1',
          position: 'right',
          gridLines: {
            color: 'rgba(255,0,0,0.3)',
          },
          ticks: {
            fontColor: 'black',
          },
        },
      ],
    },
    annotation: {
      annotations: [],
    },
  };
  public ChartColors: Color[] = [
    {
      // orange
      backgroundColor: 'rgba(253,117,0,0.2)',
      borderColor: '#fd7500',
      pointBackgroundColor: '#fd7500',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(254,144,1,0.8)',
    },
    {
      // green
      backgroundColor: 'rgba(57,228,0,0.2)',
      borderColor: 'rgba(57,228,0,1)',
      pointBackgroundColor: 'rgba(57,228,0,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(57,228,0,1)',
    },
    {
      // brown
      backgroundColor: 'rgba(106,72,40,0.3)',
      borderColor: 'rgba(106,72,40,1)',
      pointBackgroundColor: 'rgba(106,72,40,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(106,72,40,0.8)',
    },
    {
      // accent (blue)
      backgroundColor: 'rgba(58,80,218,0.3)',
      borderColor: 'rgba(58,80,218,1)',
      pointBackgroundColor: 'rgba(58,80,218,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(58,80,218,0.8)',
    },
  ];
  public ChartLegend = true;
  public ChartType: ChartType = this.mode;
  public lineChartPlugins = [];

  @ViewChild(BaseChartDirective, { static: true }) chart?: BaseChartDirective;

  constructor() {}

  ngOnInit(): void {}
}
