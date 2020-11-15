import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppedSharedModule } from 'app/shared/shared.module';
import { TagTypeComponent } from './tag-type.component';
import { TagTypeDetailComponent } from './tag-type-detail.component';
import { TagTypeUpdateComponent } from './tag-type-update.component';
import { TagTypeDeleteDialogComponent } from './tag-type-delete-dialog.component';
import { tagTypeRoute } from './tag-type.route';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild(tagTypeRoute)],
  declarations: [TagTypeComponent, TagTypeDetailComponent, TagTypeUpdateComponent, TagTypeDeleteDialogComponent],
  entryComponents: [TagTypeDetailComponent, TagTypeDeleteDialogComponent],
})
export class ShoppedTagTypeModule {}
