import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPost, Post } from 'app/shared/model/post.model';
import { PostService } from './post.service';
import { PostComponent } from './post.component';
import { PostDetailComponent } from './post-detail.component';
import { PostUpdateComponent } from './post-update.component';
import { PostUserListComponent } from 'app/entities/post/post-user-list.component';
import { RecipeResolve } from 'app/entities/recipe/recipe.route';

@Injectable({ providedIn: 'root' })
export class PostResolve implements Resolve<IPost> {
  constructor(private service: PostService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPost> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((post: HttpResponse<Post>) => {
          if (post.body) {
            return of(post.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Post());
  }
}

export const postRoute: Routes = [
  {
    path: '',
    component: PostComponent,
    data: {
      authorities: [Authority.ADMIN],
      defaultSort: 'id,asc',
      pageTitle: 'shoppedApp.post.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PostDetailComponent,
    resolve: {
      post: PostResolve,
    },
    data: {
      authorities: [Authority.USER, Authority.ADMIN],
      pageTitle: 'shoppedApp.post.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PostUpdateComponent,
    resolve: {
      post: PostResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.post.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PostUpdateComponent,
    resolve: {
      post: PostResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.post.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'list',
    component: PostUserListComponent,
    resolve: {
      recipe: RecipeResolve,
    },
    data: {
      authorities: [Authority.USER, Authority.ADMIN],
      pageTitle: 'shoppedApp.recipe.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
