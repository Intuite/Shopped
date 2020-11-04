import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppedSharedModule } from 'app/shared/shared.module';
import { BiteComponent } from './bite.component';
import { BiteDetailComponent } from './bite-detail.component';
import { BiteUpdateComponent } from './bite-update.component';
import { BiteDeleteDialogComponent } from './bite-delete-dialog.component';
import { biteRoute } from './bite.route';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild(biteRoute)],
  declarations: [BiteComponent, BiteDetailComponent, BiteUpdateComponent, BiteDeleteDialogComponent],
  entryComponents: [BiteDeleteDialogComponent],
})
export class ShoppedBiteModule {}
