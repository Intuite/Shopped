import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommendation } from 'app/shared/model/commendation.model';
import { CommendationService } from './commendation.service';

@Component({
  templateUrl: './commendation-delete-dialog.component.html',
})
export class CommendationDeleteDialogComponent {
  commendation?: ICommendation;

  constructor(
    protected commendationService: CommendationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.commendationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('commendationListModification');
      this.activeModal.close();
    });
  }
}
