import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITagType, TagType } from 'app/shared/model/tag-type.model';
import { TagTypeService } from './tag-type.service';
import { TagTypeComponent } from './tag-type.component';
import { TagTypeDetailComponent } from './tag-type-detail.component';
import { TagTypeUpdateComponent } from './tag-type-update.component';

@Injectable({ providedIn: 'root' })
export class TagTypeResolve implements Resolve<ITagType> {
  constructor(private service: TagTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITagType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tagType: HttpResponse<TagType>) => {
          if (tagType.body) {
            return of(tagType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TagType());
  }
}

export const tagTypeRoute: Routes = [
  {
    path: '',
    component: TagTypeComponent,
    data: {
      authorities: [Authority.ADMIN],
      defaultSort: 'id,asc',
      pageTitle: 'shoppedApp.tagType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TagTypeDetailComponent,
    resolve: {
      tagType: TagTypeResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'shoppedApp.tagType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TagTypeUpdateComponent,
    resolve: {
      tagType: TagTypeResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'shoppedApp.tagType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TagTypeUpdateComponent,
    resolve: {
      tagType: TagTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.tagType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
