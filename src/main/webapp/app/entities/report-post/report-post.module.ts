import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppedSharedModule } from 'app/shared/shared.module';
import { ReportPostComponent } from './report-post.component';
import { ReportPostDetailComponent } from './report-post-detail.component';
import { ReportPostUpdateComponent } from './report-post-update.component';
import { ReportPostDeleteDialogComponent } from './report-post-delete-dialog.component';
import { reportPostRoute } from './report-post.route';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild(reportPostRoute)],
  declarations: [ReportPostComponent, ReportPostDetailComponent, ReportPostUpdateComponent, ReportPostDeleteDialogComponent],
  entryComponents: [ReportPostDeleteDialogComponent],
})
export class ShoppedReportPostModule {}
