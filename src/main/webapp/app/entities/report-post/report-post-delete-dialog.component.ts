import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReportPost } from 'app/shared/model/report-post.model';
import { ReportPostService } from './report-post.service';

@Component({
  templateUrl: './report-post-delete-dialog.component.html',
})
export class ReportPostDeleteDialogComponent {
  reportPost?: IReportPost;

  constructor(
    protected reportPostService: ReportPostService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.reportPostService.delete(id).subscribe(() => {
      this.eventManager.broadcast('reportPostListModification');
      this.activeModal.close();
    });
  }
}
