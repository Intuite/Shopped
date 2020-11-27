import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILog, Log } from 'app/shared/model/log.model';
import { LogService } from './log.service';
import { LogComponent } from './log.component';
import { LogDetailComponent } from './log-detail.component';
import { LogUpdateComponent } from './log-update.component';

@Injectable({ providedIn: 'root' })
export class LogResolve implements Resolve<ILog> {
  constructor(private service: LogService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILog> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((log: HttpResponse<Log>) => {
          if (log.body) {
            return of(log.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Log());
  }
}

export const logRoute: Routes = [
  {
    path: '',
    component: LogComponent,
    data: {
      authorities: [Authority.ADMIN],
      defaultSort: 'id,asc',
      pageTitle: 'shoppedApp.log.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LogDetailComponent,
    resolve: {
      log: LogResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'shoppedApp.log.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LogUpdateComponent,
    resolve: {
      log: LogResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'shoppedApp.log.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LogUpdateComponent,
    resolve: {
      log: LogResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'shoppedApp.log.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
