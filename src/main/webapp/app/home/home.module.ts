import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppedSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { MatChipsModule } from '@angular/material/chips';
import { PostHomeComponent } from './post-home/post-home.component';
import { PostFilterPipe } from './post-home/post-filter.pipe';
import { ReversePipe } from './post-home/reverse.pipe';
import { TopsComponent } from './tops/tops.component';
import { RecomendComponent } from './recomend/recomend.component';
import { CounterFilterPipe } from './tops/counter-filter.pipe';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild([HOME_ROUTE]), MatChipsModule],
  declarations: [HomeComponent, PostHomeComponent, PostFilterPipe, ReversePipe, TopsComponent, RecomendComponent, CounterFilterPipe],
})
export class ShoppedHomeModule {}
