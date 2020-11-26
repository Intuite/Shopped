import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppedSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { MatChipsModule } from '@angular/material/chips';
import { PostHomeComponent } from './post-home/post-home.component';
import { PostFilterPipe } from './post-home/post-filter.pipe';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild([HOME_ROUTE]), MatChipsModule],
  declarations: [HomeComponent, PostHomeComponent, PostFilterPipe],
})
export class ShoppedHomeModule {}
