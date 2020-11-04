import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReportType } from 'app/shared/model/report-type.model';
import { ReportTypeService } from './report-type.service';

@Component({
  templateUrl: './report-type-delete-dialog.component.html',
})
export class ReportTypeDeleteDialogComponent {
  reportType?: IReportType;

  constructor(
    protected reportTypeService: ReportTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.reportTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('reportTypeListModification');
      this.activeModal.close();
    });
  }
}
