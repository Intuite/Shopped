import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReportComment } from 'app/shared/model/report-comment.model';
import { ReportCommentService } from './report-comment.service';

@Component({
  templateUrl: './report-comment-delete-dialog.component.html',
})
export class ReportCommentDeleteDialogComponent {
  reportComment?: IReportComment;

  constructor(
    protected reportCommentService: ReportCommentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.reportCommentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('reportCommentListModification');
      this.activeModal.close();
    });
  }
}
