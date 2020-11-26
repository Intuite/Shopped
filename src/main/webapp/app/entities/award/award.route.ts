import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAward, Award } from 'app/shared/model/award.model';
import { AwardService } from './award.service';
import { AwardComponent } from './award.component';
import { AwardDetailComponent } from './award-detail.component';
import { AwardUpdateComponent } from './award-update.component';

@Injectable({ providedIn: 'root' })
export class AwardResolve implements Resolve<IAward> {
  constructor(private service: AwardService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAward> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((award: HttpResponse<Award>) => {
          if (award.body) {
            return of(award.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Award());
  }
}

export const awardRoute: Routes = [
  {
    path: '',
    component: AwardComponent,
    data: {
      authorities: [Authority.ADMIN],
      defaultSort: 'id,asc',
      pageTitle: 'shoppedApp.award.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AwardDetailComponent,
    resolve: {
      award: AwardResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'shoppedApp.award.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AwardUpdateComponent,
    resolve: {
      award: AwardResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'shoppedApp.award.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AwardUpdateComponent,
    resolve: {
      award: AwardResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'shoppedApp.award.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
