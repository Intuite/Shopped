import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppedSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { MatChipsModule } from '@angular/material/chips';
import { PostComponent } from 'app/home/post/post.component';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild([HOME_ROUTE]), MatChipsModule, PostComponent],
  declarations: [HomeComponent],
})
export class ShoppedHomeModule {}
