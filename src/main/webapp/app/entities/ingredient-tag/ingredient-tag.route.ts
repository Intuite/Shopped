import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIngredientTag, IngredientTag } from 'app/shared/model/ingredient-tag.model';
import { IngredientTagService } from './ingredient-tag.service';
import { IngredientTagComponent } from './ingredient-tag.component';
import { IngredientTagDetailComponent } from './ingredient-tag-detail.component';
import { IngredientTagUpdateComponent } from './ingredient-tag-update.component';

@Injectable({ providedIn: 'root' })
export class IngredientTagResolve implements Resolve<IIngredientTag> {
  constructor(private service: IngredientTagService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIngredientTag> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((ingredientTag: HttpResponse<IngredientTag>) => {
          if (ingredientTag.body) {
            return of(ingredientTag.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new IngredientTag());
  }
}

export const ingredientTagRoute: Routes = [
  {
    path: '',
    component: IngredientTagComponent,
    data: {
      authorities: [Authority.ADMIN],
      defaultSort: 'id,asc',
      pageTitle: 'shoppedApp.ingredientTag.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IngredientTagDetailComponent,
    resolve: {
      ingredientTag: IngredientTagResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'shoppedApp.ingredientTag.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IngredientTagUpdateComponent,
    resolve: {
      ingredientTag: IngredientTagResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'shoppedApp.ingredientTag.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IngredientTagUpdateComponent,
    resolve: {
      ingredientTag: IngredientTagResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.ingredientTag.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
