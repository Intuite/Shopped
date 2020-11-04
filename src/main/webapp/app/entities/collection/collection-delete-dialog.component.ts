import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICollection } from 'app/shared/model/collection.model';
import { CollectionService } from './collection.service';

@Component({
  templateUrl: './collection-delete-dialog.component.html',
})
export class CollectionDeleteDialogComponent {
  collection?: ICollection;

  constructor(
    protected collectionService: CollectionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.collectionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('collectionListModification');
      this.activeModal.close();
    });
  }
}
