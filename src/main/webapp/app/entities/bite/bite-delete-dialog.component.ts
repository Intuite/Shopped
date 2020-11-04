import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBite } from 'app/shared/model/bite.model';
import { BiteService } from './bite.service';

@Component({
  templateUrl: './bite-delete-dialog.component.html',
})
export class BiteDeleteDialogComponent {
  bite?: IBite;

  constructor(protected biteService: BiteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.biteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('biteListModification');
      this.activeModal.close();
    });
  }
}
