import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRecipeHasRecipeTag, RecipeHasRecipeTag } from 'app/shared/model/recipe-has-recipe-tag.model';
import { RecipeHasRecipeTagService } from './recipe-has-recipe-tag.service';
import { RecipeHasRecipeTagComponent } from './recipe-has-recipe-tag.component';
import { RecipeHasRecipeTagDetailComponent } from './recipe-has-recipe-tag-detail.component';
import { RecipeHasRecipeTagUpdateComponent } from './recipe-has-recipe-tag-update.component';

@Injectable({ providedIn: 'root' })
export class RecipeHasRecipeTagResolve implements Resolve<IRecipeHasRecipeTag> {
  constructor(private service: RecipeHasRecipeTagService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRecipeHasRecipeTag> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((recipeHasRecipeTag: HttpResponse<RecipeHasRecipeTag>) => {
          if (recipeHasRecipeTag.body) {
            return of(recipeHasRecipeTag.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RecipeHasRecipeTag());
  }
}

export const recipeHasRecipeTagRoute: Routes = [
  {
    path: '',
    component: RecipeHasRecipeTagComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'shoppedApp.recipeHasRecipeTag.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RecipeHasRecipeTagDetailComponent,
    resolve: {
      recipeHasRecipeTag: RecipeHasRecipeTagResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.recipeHasRecipeTag.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RecipeHasRecipeTagUpdateComponent,
    resolve: {
      recipeHasRecipeTag: RecipeHasRecipeTagResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.recipeHasRecipeTag.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RecipeHasRecipeTagUpdateComponent,
    resolve: {
      recipeHasRecipeTag: RecipeHasRecipeTagResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.recipeHasRecipeTag.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
