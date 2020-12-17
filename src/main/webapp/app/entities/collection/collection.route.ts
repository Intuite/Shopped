import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { ICollection, Collection } from 'app/shared/model/collection.model';
import { CollectionService } from './collection.service';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Authority } from 'app/shared/constants/authority.constants';
import { CollectionComponent } from 'app/entities/collection/collection.component';

@Injectable({ providedIn: 'root' })
export class CollectionResolve implements Resolve<ICollection> {
  constructor(private service: CollectionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICollection> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((collection: HttpResponse<Collection>) => {
          if (collection.body) {
            return of(collection.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Collection());
  }
}

export const collectionRoute: Routes = [
  {
    path: '',
    component: CollectionComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'shoppedApp.collection.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  // {
  //   path: ':id/view',
  //   component: CollectionDetailComponent,
  //   resolve: {
  //     collection: CollectionResolve,
  //   },
  //   data: {
  //     authorities: [Authority.USER],
  //     pageTitle: 'shoppedApp.collection.home.title',
  //   },
  //   canActivate: [UserRouteAccessService],
  // },
  // {
  //   path: 'new',
  //   component: CollectionUpdateComponent,
  //   resolve: {
  //     collection: CollectionResolve,
  //   },
  //   data: {
  //     authorities: [Authority.USER],
  //     pageTitle: 'shoppedApp.collection.home.title',
  //   },
  //   canActivate: [UserRouteAccessService],
  // },
  // {
  //   path: ':id/edit',
  //   component: CollectionUpdateComponent,
  //   resolve: {
  //     collection: CollectionResolve,
  //   },
  //   data: {
  //     authorities: [Authority.USER],
  //     pageTitle: 'shoppedApp.collection.home.title',
  //   },
  //   canActivate: [UserRouteAccessService],
  // },
];
