import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAward } from 'app/shared/model/award.model';
import { AwardService } from './award.service';

@Component({
  templateUrl: './award-delete-dialog.component.html',
})
export class AwardDeleteDialogComponent {
  award?: IAward;

  constructor(protected awardService: AwardService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.awardService.delete(id).subscribe(() => {
      this.eventManager.broadcast('awardListModification');
      this.activeModal.close();
    });
    window.location.reload();
  }
}
