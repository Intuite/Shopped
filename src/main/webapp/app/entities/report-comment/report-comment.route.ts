import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IReportComment, ReportComment } from 'app/shared/model/report-comment.model';
import { ReportCommentService } from './report-comment.service';
import { ReportCommentComponent } from './report-comment.component';
import { ReportCommentDetailComponent } from './report-comment-detail.component';
import { ReportCommentUpdateComponent } from './report-comment-update.component';

@Injectable({ providedIn: 'root' })
export class ReportCommentResolve implements Resolve<IReportComment> {
  constructor(private service: ReportCommentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReportComment> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((reportComment: HttpResponse<ReportComment>) => {
          if (reportComment.body) {
            return of(reportComment.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ReportComment());
  }
}

export const reportCommentRoute: Routes = [
  {
    path: '',
    component: ReportCommentComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'shoppedApp.reportComment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ReportCommentDetailComponent,
    resolve: {
      reportComment: ReportCommentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.reportComment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ReportCommentUpdateComponent,
    resolve: {
      reportComment: ReportCommentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.reportComment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ReportCommentUpdateComponent,
    resolve: {
      reportComment: ReportCommentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.reportComment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
