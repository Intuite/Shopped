import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIngredientHasIngredientTag, IngredientHasIngredientTag } from 'app/shared/model/ingredient-has-ingredient-tag.model';
import { IngredientHasIngredientTagService } from './ingredient-has-ingredient-tag.service';
import { IngredientHasIngredientTagComponent } from './ingredient-has-ingredient-tag.component';
import { IngredientHasIngredientTagDetailComponent } from './ingredient-has-ingredient-tag-detail.component';
import { IngredientHasIngredientTagUpdateComponent } from './ingredient-has-ingredient-tag-update.component';

@Injectable({ providedIn: 'root' })
export class IngredientHasIngredientTagResolve implements Resolve<IIngredientHasIngredientTag> {
  constructor(private service: IngredientHasIngredientTagService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIngredientHasIngredientTag> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((ingredientHasIngredientTag: HttpResponse<IngredientHasIngredientTag>) => {
          if (ingredientHasIngredientTag.body) {
            return of(ingredientHasIngredientTag.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new IngredientHasIngredientTag());
  }
}

export const ingredientHasIngredientTagRoute: Routes = [
  {
    path: '',
    component: IngredientHasIngredientTagComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'shoppedApp.ingredientHasIngredientTag.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IngredientHasIngredientTagDetailComponent,
    resolve: {
      ingredientHasIngredientTag: IngredientHasIngredientTagResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.ingredientHasIngredientTag.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IngredientHasIngredientTagUpdateComponent,
    resolve: {
      ingredientHasIngredientTag: IngredientHasIngredientTagResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.ingredientHasIngredientTag.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IngredientHasIngredientTagUpdateComponent,
    resolve: {
      ingredientHasIngredientTag: IngredientHasIngredientTagResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.ingredientHasIngredientTag.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
