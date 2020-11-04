import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppedSharedModule } from 'app/shared/shared.module';
import { LogTypeComponent } from './log-type.component';
import { LogTypeDetailComponent } from './log-type-detail.component';
import { LogTypeUpdateComponent } from './log-type-update.component';
import { LogTypeDeleteDialogComponent } from './log-type-delete-dialog.component';
import { logTypeRoute } from './log-type.route';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild(logTypeRoute)],
  declarations: [LogTypeComponent, LogTypeDetailComponent, LogTypeUpdateComponent, LogTypeDeleteDialogComponent],
  entryComponents: [LogTypeDeleteDialogComponent],
})
export class ShoppedLogTypeModule {}
