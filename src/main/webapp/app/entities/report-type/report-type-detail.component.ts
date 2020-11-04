import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReportType } from 'app/shared/model/report-type.model';

@Component({
  selector: 'jhi-report-type-detail',
  templateUrl: './report-type-detail.component.html',
})
export class ReportTypeDetailComponent implements OnInit {
  reportType: IReportType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reportType }) => (this.reportType = reportType));
  }

  previousState(): void {
    window.history.back();
  }
}
