import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICommendation, Commendation } from 'app/shared/model/commendation.model';
import { CommendationService } from './commendation.service';
import { CommendationComponent } from './commendation.component';
import { CommendationDetailComponent } from './commendation-detail.component';
import { CommendationUpdateComponent } from './commendation-update.component';

@Injectable({ providedIn: 'root' })
export class CommendationResolve implements Resolve<ICommendation> {
  constructor(private service: CommendationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommendation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((commendation: HttpResponse<Commendation>) => {
          if (commendation.body) {
            return of(commendation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Commendation());
  }
}

export const commendationRoute: Routes = [
  {
    path: '',
    component: CommendationComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'shoppedApp.commendation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CommendationDetailComponent,
    resolve: {
      commendation: CommendationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.commendation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CommendationUpdateComponent,
    resolve: {
      commendation: CommendationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.commendation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CommendationUpdateComponent,
    resolve: {
      commendation: CommendationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.commendation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
