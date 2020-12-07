import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { ChartOptions, ChartType } from 'chart.js';
import { BaseChartDirective, Label } from 'ng2-charts';

@Component({
  selector: 'jhi-pie-chart',
  templateUrl: './pie-chart.component.html',
  styleUrls: ['./pie-chart.component.scss'],
})
export class PieChartComponent implements OnInit {
  @ViewChild(BaseChartDirective, { static: true }) chart?: BaseChartDirective;
  @Input() pieChartLabels: Label[] = [];
  @Input() pieChartData: number[] = [];
  @Input() width = 400;

  // Pie
  public pieChartOptions: ChartOptions = {
    responsive: true,
    legend: {
      position: 'left',
    },
  };
  public pieChartType: ChartType = 'pie';
  public pieChartLegend = true;
  public pieChartPlugins = [];
  public pieChartColors = [
    {
      backgroundColor: ['#fd7500', 'rgba(57,228,0,1)', 'rgba(106,72,40,1)', 'rgba(58,80,218,1)'],
    },
  ];

  constructor() {}

  ngOnInit(): void {}
}
