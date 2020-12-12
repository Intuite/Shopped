import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IReportType, ReportType } from 'app/shared/model/report-type.model';
import { ReportTypeService } from './report-type.service';
import { ReportTypeComponent } from './report-type.component';
import { ReportTypeDetailComponent } from './report-type-detail.component';
import { ReportTypeUpdateComponent } from './report-type-update.component';

@Injectable({ providedIn: 'root' })
export class ReportTypeResolve implements Resolve<IReportType> {
  constructor(private service: ReportTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReportType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((reportType: HttpResponse<ReportType>) => {
          if (reportType.body) {
            return of(reportType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ReportType());
  }
}

export const reportTypeRoute: Routes = [
  {
    path: '',
    component: ReportTypeComponent,
    data: {
      authorities: [Authority.ADMIN],
      defaultSort: 'id,asc',
      pageTitle: 'shoppedApp.reportType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ReportTypeDetailComponent,
    resolve: {
      reportType: ReportTypeResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'shoppedApp.reportType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ReportTypeUpdateComponent,
    resolve: {
      reportType: ReportTypeResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'shoppedApp.reportType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ReportTypeUpdateComponent,
    resolve: {
      reportType: ReportTypeResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'shoppedApp.reportType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
