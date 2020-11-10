import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBundle, Bundle } from 'app/shared/model/bundle.model';
import { BundleService } from './bundle.service';
import { BundleComponent } from './bundle.component';
import { BundleDetailComponent } from './bundle-detail.component';
import { BundleUpdateComponent } from './bundle-update.component';

@Injectable({ providedIn: 'root' })
export class BundleResolve implements Resolve<IBundle> {
  constructor(private service: BundleService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBundle> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((bundle: HttpResponse<Bundle>) => {
          if (bundle.body) {
            return of(bundle.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Bundle());
  }
}

export const bundleRoute: Routes = [
  {
    path: '',
    component: BundleComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.bundle.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BundleDetailComponent,
    resolve: {
      bundle: BundleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.bundle.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BundleUpdateComponent,
    resolve: {
      bundle: BundleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.bundle.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BundleUpdateComponent,
    resolve: {
      bundle: BundleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.bundle.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
