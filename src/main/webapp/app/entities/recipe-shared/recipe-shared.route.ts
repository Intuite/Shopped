import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRecipeShared, RecipeShared } from 'app/shared/model/recipe-shared.model';
import { RecipeSharedService } from './recipe-shared.service';
import { RecipeSharedComponent } from './recipe-shared.component';
import { RecipeSharedDetailComponent } from './recipe-shared-detail.component';
import { RecipeSharedUpdateComponent } from './recipe-shared-update.component';

@Injectable({ providedIn: 'root' })
export class RecipeSharedResolve implements Resolve<IRecipeShared> {
  constructor(private service: RecipeSharedService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRecipeShared> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((recipeShared: HttpResponse<RecipeShared>) => {
          if (recipeShared.body) {
            return of(recipeShared.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RecipeShared());
  }
}

export const recipeSharedRoute: Routes = [
  {
    path: '',
    component: RecipeSharedComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'shoppedApp.recipeShared.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RecipeSharedDetailComponent,
    resolve: {
      recipeShared: RecipeSharedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.recipeShared.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RecipeSharedUpdateComponent,
    resolve: {
      recipeShared: RecipeSharedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.recipeShared.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RecipeSharedUpdateComponent,
    resolve: {
      recipeShared: RecipeSharedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.recipeShared.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
