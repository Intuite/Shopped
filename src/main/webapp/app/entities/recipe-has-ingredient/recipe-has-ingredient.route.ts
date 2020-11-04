import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRecipeHasIngredient, RecipeHasIngredient } from 'app/shared/model/recipe-has-ingredient.model';
import { RecipeHasIngredientService } from './recipe-has-ingredient.service';
import { RecipeHasIngredientComponent } from './recipe-has-ingredient.component';
import { RecipeHasIngredientDetailComponent } from './recipe-has-ingredient-detail.component';
import { RecipeHasIngredientUpdateComponent } from './recipe-has-ingredient-update.component';

@Injectable({ providedIn: 'root' })
export class RecipeHasIngredientResolve implements Resolve<IRecipeHasIngredient> {
  constructor(private service: RecipeHasIngredientService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRecipeHasIngredient> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((recipeHasIngredient: HttpResponse<RecipeHasIngredient>) => {
          if (recipeHasIngredient.body) {
            return of(recipeHasIngredient.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RecipeHasIngredient());
  }
}

export const recipeHasIngredientRoute: Routes = [
  {
    path: '',
    component: RecipeHasIngredientComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'shoppedApp.recipeHasIngredient.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RecipeHasIngredientDetailComponent,
    resolve: {
      recipeHasIngredient: RecipeHasIngredientResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.recipeHasIngredient.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RecipeHasIngredientUpdateComponent,
    resolve: {
      recipeHasIngredient: RecipeHasIngredientResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.recipeHasIngredient.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RecipeHasIngredientUpdateComponent,
    resolve: {
      recipeHasIngredient: RecipeHasIngredientResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.recipeHasIngredient.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
