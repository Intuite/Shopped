import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFollower } from 'app/shared/model/follower.model';
import { FollowerService } from './follower.service';

@Component({
  templateUrl: './follower-delete-dialog.component.html',
})
export class FollowerDeleteDialogComponent {
  follower?: IFollower;

  constructor(protected followerService: FollowerService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.followerService.delete(id).subscribe(() => {
      this.eventManager.broadcast('followerListModification');
      this.activeModal.close();
    });
  }
}
