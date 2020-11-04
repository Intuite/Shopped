import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILogType } from 'app/shared/model/log-type.model';
import { LogTypeService } from './log-type.service';

@Component({
  templateUrl: './log-type-delete-dialog.component.html',
})
export class LogTypeDeleteDialogComponent {
  logType?: ILogType;

  constructor(protected logTypeService: LogTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.logTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('logTypeListModification');
      this.activeModal.close();
    });
  }
}
