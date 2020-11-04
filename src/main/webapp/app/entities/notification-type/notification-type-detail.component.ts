import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INotificationType } from 'app/shared/model/notification-type.model';

@Component({
  selector: 'jhi-notification-type-detail',
  templateUrl: './notification-type-detail.component.html',
})
export class NotificationTypeDetailComponent implements OnInit {
  notificationType: INotificationType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notificationType }) => (this.notificationType = notificationType));
  }

  previousState(): void {
    window.history.back();
  }
}
