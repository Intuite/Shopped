import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppedSharedModule } from 'app/shared/shared.module';
import { CollectionComponent } from './collection.component';
import { CollectionDetailComponent } from './collection-detail.component';
import { CollectionUpdateComponent } from './collection-update.component';
import { CollectionDeleteDialogComponent } from './collection-delete-dialog.component';
import { collectionRoute } from './collection.route';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild(collectionRoute)],
  declarations: [CollectionComponent, CollectionDetailComponent, CollectionUpdateComponent, CollectionDeleteDialogComponent],
  entryComponents: [CollectionDeleteDialogComponent],
})
export class ShoppedCollectionModule {}
