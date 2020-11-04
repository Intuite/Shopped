import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INotificationType } from 'app/shared/model/notification-type.model';
import { NotificationTypeService } from './notification-type.service';

@Component({
  templateUrl: './notification-type-delete-dialog.component.html',
})
export class NotificationTypeDeleteDialogComponent {
  notificationType?: INotificationType;

  constructor(
    protected notificationTypeService: NotificationTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.notificationTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('notificationTypeListModification');
      this.activeModal.close();
    });
  }
}
