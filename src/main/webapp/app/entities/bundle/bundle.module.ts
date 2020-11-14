import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppedSharedModule } from 'app/shared/shared.module';
import { BundleComponent } from './bundle.component';
import { BundleDetailComponent } from './bundle-detail.component';
import { BundleUpdateComponent } from './bundle-update.component';
import { BundleDeleteDialogComponent } from './bundle-delete-dialog.component';
import { bundleRoute } from './bundle.route';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild(bundleRoute)],
  declarations: [BundleComponent, BundleDetailComponent, BundleUpdateComponent, BundleDeleteDialogComponent],
  entryComponents: [BundleDeleteDialogComponent],
})
export class ShoppedBundleModule {}
