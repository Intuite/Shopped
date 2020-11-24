import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, Router, Routes } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRecipeTag, RecipeTag } from 'app/shared/model/recipe-tag.model';
import { RecipeTagService } from './recipe-tag.service';
import { RecipeTagComponent } from './recipe-tag.component';
import { RecipeTagUpdateComponent } from './recipe-tag-update.component';

@Injectable({ providedIn: 'root' })
export class RecipeTagResolve implements Resolve<IRecipeTag> {
  constructor(private service: RecipeTagService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRecipeTag> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((recipeTag: HttpResponse<RecipeTag>) => {
          if (recipeTag.body) {
            return of(recipeTag.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RecipeTag());
  }
}

export const recipeTagRoute: Routes = [
  {
    path: '',
    component: RecipeTagComponent,
    data: {
      authorities: [Authority.ADMIN],
      defaultSort: 'id,asc',
      pageTitle: 'shoppedApp.recipeTag.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RecipeTagUpdateComponent,
    resolve: {
      recipeTag: RecipeTagResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'shoppedApp.recipeTag.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RecipeTagUpdateComponent,
    resolve: {
      recipeTag: RecipeTagResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'shoppedApp.recipeTag.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
