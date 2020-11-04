import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReportComment } from 'app/shared/model/report-comment.model';

@Component({
  selector: 'jhi-report-comment-detail',
  templateUrl: './report-comment-detail.component.html',
})
export class ReportCommentDetailComponent implements OnInit {
  reportComment: IReportComment | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reportComment }) => (this.reportComment = reportComment));
  }

  previousState(): void {
    window.history.back();
  }
}
