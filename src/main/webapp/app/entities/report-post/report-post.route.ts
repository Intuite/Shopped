import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IReportPost, ReportPost } from 'app/shared/model/report-post.model';
import { ReportPostService } from './report-post.service';
import { ReportPostComponent } from './report-post.component';
import { ReportPostDetailComponent } from './report-post-detail.component';
import { ReportPostUpdateComponent } from './report-post-update.component';

@Injectable({ providedIn: 'root' })
export class ReportPostResolve implements Resolve<IReportPost> {
  constructor(private service: ReportPostService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReportPost> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((reportPost: HttpResponse<ReportPost>) => {
          if (reportPost.body) {
            return of(reportPost.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ReportPost());
  }
}

export const reportPostRoute: Routes = [
  {
    path: '',
    component: ReportPostComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'shoppedApp.reportPost.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ReportPostDetailComponent,
    resolve: {
      reportPost: ReportPostResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.reportPost.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ReportPostUpdateComponent,
    resolve: {
      reportPost: ReportPostResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.reportPost.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ReportPostUpdateComponent,
    resolve: {
      reportPost: ReportPostResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.reportPost.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
