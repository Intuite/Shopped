import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INotificationType, NotificationType } from 'app/shared/model/notification-type.model';
import { NotificationTypeService } from './notification-type.service';
import { NotificationTypeComponent } from './notification-type.component';
import { NotificationTypeDetailComponent } from './notification-type-detail.component';
import { NotificationTypeUpdateComponent } from './notification-type-update.component';

@Injectable({ providedIn: 'root' })
export class NotificationTypeResolve implements Resolve<INotificationType> {
  constructor(private service: NotificationTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INotificationType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((notificationType: HttpResponse<NotificationType>) => {
          if (notificationType.body) {
            return of(notificationType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NotificationType());
  }
}

export const notificationTypeRoute: Routes = [
  {
    path: '',
    component: NotificationTypeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'shoppedApp.notificationType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NotificationTypeDetailComponent,
    resolve: {
      notificationType: NotificationTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.notificationType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NotificationTypeUpdateComponent,
    resolve: {
      notificationType: NotificationTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.notificationType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NotificationTypeUpdateComponent,
    resolve: {
      notificationType: NotificationTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.notificationType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
