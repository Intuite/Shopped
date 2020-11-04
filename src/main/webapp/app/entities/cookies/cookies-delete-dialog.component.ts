import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICookies } from 'app/shared/model/cookies.model';
import { CookiesService } from './cookies.service';

@Component({
  templateUrl: './cookies-delete-dialog.component.html',
})
export class CookiesDeleteDialogComponent {
  cookies?: ICookies;

  constructor(protected cookiesService: CookiesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cookiesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('cookiesListModification');
      this.activeModal.close();
    });
  }
}
