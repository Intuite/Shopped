import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBundle } from 'app/shared/model/bundle.model';
import { BundleService } from './bundle.service';

@Component({
  templateUrl: './bundle-delete-dialog.component.html',
})
export class BundleDeleteDialogComponent {
  bundle?: IBundle;

  constructor(protected bundleService: BundleService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bundleService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bundleListModification');
      this.activeModal.close();
    });
  }
}
