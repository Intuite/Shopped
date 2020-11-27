import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppedSharedModule } from 'app/shared/shared.module';
import { PostComponent } from './post.component';
import { PostDetailComponent } from './post-detail.component';
import { PostUpdateComponent } from './post-update.component';
import { PostDeleteDialogComponent } from './post-delete-dialog.component';
import { PostUserListComponent } from 'app/entities/post/post-user-list.component';
import { postRoute } from './post.route';
import { PostFilterPipe } from './post-filter.pipe';

@NgModule({
  imports: [ShoppedSharedModule, RouterModule.forChild(postRoute)],
  declarations: [PostComponent, PostDetailComponent, PostUpdateComponent, PostDeleteDialogComponent, PostUserListComponent, PostFilterPipe],
  entryComponents: [PostDeleteDialogComponent],
  exports: [PostComponent, PostUserListComponent],
})
export class ShoppedPostModule {}
