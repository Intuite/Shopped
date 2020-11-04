import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBite, Bite } from 'app/shared/model/bite.model';
import { BiteService } from './bite.service';
import { BiteComponent } from './bite.component';
import { BiteDetailComponent } from './bite-detail.component';
import { BiteUpdateComponent } from './bite-update.component';

@Injectable({ providedIn: 'root' })
export class BiteResolve implements Resolve<IBite> {
  constructor(private service: BiteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBite> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((bite: HttpResponse<Bite>) => {
          if (bite.body) {
            return of(bite.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Bite());
  }
}

export const biteRoute: Routes = [
  {
    path: '',
    component: BiteComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'shoppedApp.bite.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BiteDetailComponent,
    resolve: {
      bite: BiteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.bite.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BiteUpdateComponent,
    resolve: {
      bite: BiteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.bite.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BiteUpdateComponent,
    resolve: {
      bite: BiteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.bite.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
