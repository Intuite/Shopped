import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppedSharedModule } from 'app/shared/shared.module';
import { CookiesComponent } from './cookies.component';
import { CookiesDetailComponent } from './cookies-detail.component';
import { CookiesUpdateComponent } from './cookies-update.component';
import { CookiesDeleteDialogComponent } from './cookies-delete-dialog.component';
import { cookiesRoute } from './cookies.route';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild(cookiesRoute)],
  declarations: [CookiesComponent, CookiesDetailComponent, CookiesUpdateComponent, CookiesDeleteDialogComponent],
  entryComponents: [CookiesDeleteDialogComponent],
})
export class ShoppedCookiesModule {}
