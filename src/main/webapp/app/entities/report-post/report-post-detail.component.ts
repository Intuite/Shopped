import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReportPost } from 'app/shared/model/report-post.model';

@Component({
  selector: 'jhi-report-post-detail',
  templateUrl: './report-post-detail.component.html',
})
export class ReportPostDetailComponent implements OnInit {
  reportPost: IReportPost | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reportPost }) => (this.reportPost = reportPost));
  }

  previousState(): void {
    window.history.back();
  }
}
