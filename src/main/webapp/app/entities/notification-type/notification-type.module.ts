import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppedSharedModule } from 'app/shared/shared.module';
import { NotificationTypeComponent } from './notification-type.component';
import { NotificationTypeDetailComponent } from './notification-type-detail.component';
import { NotificationTypeUpdateComponent } from './notification-type-update.component';
import { NotificationTypeDeleteDialogComponent } from './notification-type-delete-dialog.component';
import { notificationTypeRoute } from './notification-type.route';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild(notificationTypeRoute)],
  declarations: [
    NotificationTypeComponent,
    NotificationTypeDetailComponent,
    NotificationTypeUpdateComponent,
    NotificationTypeDeleteDialogComponent,
  ],
  entryComponents: [NotificationTypeDeleteDialogComponent],
})
export class ShoppedNotificationTypeModule {}
