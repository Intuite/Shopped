import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppedSharedModule } from 'app/shared/shared.module';
import { ReportCommentComponent } from './report-comment.component';
import { ReportCommentDetailComponent } from './report-comment-detail.component';
import { ReportCommentUpdateComponent } from './report-comment-update.component';
import { ReportCommentDeleteDialogComponent } from './report-comment-delete-dialog.component';
import { reportCommentRoute } from './report-comment.route';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild(reportCommentRoute)],
  declarations: [ReportCommentComponent, ReportCommentDetailComponent, ReportCommentUpdateComponent, ReportCommentDeleteDialogComponent],
  entryComponents: [ReportCommentDeleteDialogComponent],
})
export class ShoppedReportCommentModule {}
