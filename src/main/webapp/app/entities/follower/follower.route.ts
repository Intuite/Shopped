import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFollower, Follower } from 'app/shared/model/follower.model';
import { FollowerService } from './follower.service';
import { FollowerComponent } from './follower.component';
import { FollowerDetailComponent } from './follower-detail.component';
import { FollowerUpdateComponent } from './follower-update.component';

@Injectable({ providedIn: 'root' })
export class FollowerResolve implements Resolve<IFollower> {
  constructor(private service: FollowerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFollower> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((follower: HttpResponse<Follower>) => {
          if (follower.body) {
            return of(follower.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Follower());
  }
}

export const followerRoute: Routes = [
  {
    path: '',
    component: FollowerComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'shoppedApp.follower.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FollowerDetailComponent,
    resolve: {
      follower: FollowerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.follower.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FollowerUpdateComponent,
    resolve: {
      follower: FollowerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.follower.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FollowerUpdateComponent,
    resolve: {
      follower: FollowerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.follower.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
