import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppedSharedModule } from 'app/shared/shared.module';
import { ReportTypeComponent } from './report-type.component';
import { ReportTypeDetailComponent } from './report-type-detail.component';
import { ReportTypeUpdateComponent } from './report-type-update.component';
import { ReportTypeDeleteDialogComponent } from './report-type-delete-dialog.component';
import { reportTypeRoute } from './report-type.route';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild(reportTypeRoute)],
  declarations: [ReportTypeComponent, ReportTypeDetailComponent, ReportTypeUpdateComponent, ReportTypeDeleteDialogComponent],
  entryComponents: [ReportTypeDeleteDialogComponent],
})
export class ShoppedReportTypeModule {}
