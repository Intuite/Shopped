import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICookies, Cookies } from 'app/shared/model/cookies.model';
import { CookiesService } from './cookies.service';
import { CookiesComponent } from './cookies.component';
import { CookiesDetailComponent } from './cookies-detail.component';
import { CookiesUpdateComponent } from './cookies-update.component';

@Injectable({ providedIn: 'root' })
export class CookiesResolve implements Resolve<ICookies> {
  constructor(private service: CookiesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICookies> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cookies: HttpResponse<Cookies>) => {
          if (cookies.body) {
            return of(cookies.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Cookies());
  }
}

export const cookiesRoute: Routes = [
  {
    path: '',
    component: CookiesComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'shoppedApp.cookies.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CookiesDetailComponent,
    resolve: {
      cookies: CookiesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.cookies.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CookiesUpdateComponent,
    resolve: {
      cookies: CookiesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.cookies.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CookiesUpdateComponent,
    resolve: {
      cookies: CookiesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.cookies.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
