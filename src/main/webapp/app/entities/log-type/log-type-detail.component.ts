import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILogType } from 'app/shared/model/log-type.model';

@Component({
  selector: 'jhi-log-type-detail',
  templateUrl: './log-type-detail.component.html',
})
export class LogTypeDetailComponent implements OnInit {
  logType: ILogType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ logType }) => (this.logType = logType));
  }

  previousState(): void {
    window.history.back();
  }
}
