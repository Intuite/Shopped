import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppedSharedModule } from 'app/shared/shared.module';
import { AwardComponent } from './award.component';
import { AwardDetailComponent } from './award-detail.component';
import { AwardUpdateComponent } from './award-update.component';
import { AwardDeleteDialogComponent } from './award-delete-dialog.component';
import { awardRoute } from './award.route';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild(awardRoute)],
  declarations: [AwardComponent, AwardDetailComponent, AwardUpdateComponent, AwardDeleteDialogComponent],
  entryComponents: [AwardDeleteDialogComponent],
})
export class ShoppedAwardModule {}
