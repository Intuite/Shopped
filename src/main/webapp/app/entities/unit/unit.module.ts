import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppedSharedModule } from 'app/shared/shared.module';
import { UnitComponent } from './unit.component';
import { UnitDetailComponent } from './unit-detail.component';
import { UnitUpdateComponent } from './unit-update.component';
import { UnitDeleteDialogComponent } from './unit-delete-dialog.component';
import { unitRoute } from './unit.route';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild(unitRoute)],
  declarations: [UnitComponent, UnitDetailComponent, UnitUpdateComponent, UnitDeleteDialogComponent],
  entryComponents: [UnitDeleteDialogComponent],
})
export class ShoppedUnitModule {}
