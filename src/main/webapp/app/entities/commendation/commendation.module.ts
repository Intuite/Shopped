import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppedSharedModule } from 'app/shared/shared.module';
import { CommendationComponent } from './commendation.component';
import { CommendationDetailComponent } from './commendation-detail.component';
import { CommendationUpdateComponent } from './commendation-update.component';
import { CommendationDeleteDialogComponent } from './commendation-delete-dialog.component';
import { commendationRoute } from './commendation.route';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild(commendationRoute)],
  declarations: [CommendationComponent, CommendationDetailComponent, CommendationUpdateComponent, CommendationDeleteDialogComponent],
  entryComponents: [CommendationDeleteDialogComponent],
})
export class ShoppedCommendationModule {}
