import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILogType, LogType } from 'app/shared/model/log-type.model';
import { LogTypeService } from './log-type.service';
import { LogTypeComponent } from './log-type.component';
import { LogTypeDetailComponent } from './log-type-detail.component';
import { LogTypeUpdateComponent } from './log-type-update.component';

@Injectable({ providedIn: 'root' })
export class LogTypeResolve implements Resolve<ILogType> {
  constructor(private service: LogTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILogType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((logType: HttpResponse<LogType>) => {
          if (logType.body) {
            return of(logType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LogType());
  }
}

export const logTypeRoute: Routes = [
  {
    path: '',
    component: LogTypeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'shoppedApp.logType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LogTypeDetailComponent,
    resolve: {
      logType: LogTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.logType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LogTypeUpdateComponent,
    resolve: {
      logType: LogTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.logType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LogTypeUpdateComponent,
    resolve: {
      logType: LogTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.logType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
