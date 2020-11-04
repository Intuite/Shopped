import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILog } from 'app/shared/model/log.model';
import { LogService } from './log.service';

@Component({
  templateUrl: './log-delete-dialog.component.html',
})
export class LogDeleteDialogComponent {
  log?: ILog;

  constructor(protected logService: LogService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.logService.delete(id).subscribe(() => {
      this.eventManager.broadcast('logListModification');
      this.activeModal.close();
    });
  }
}
