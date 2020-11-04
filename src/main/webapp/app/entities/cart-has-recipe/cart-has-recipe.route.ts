import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICartHasRecipe, CartHasRecipe } from 'app/shared/model/cart-has-recipe.model';
import { CartHasRecipeService } from './cart-has-recipe.service';
import { CartHasRecipeComponent } from './cart-has-recipe.component';
import { CartHasRecipeDetailComponent } from './cart-has-recipe-detail.component';
import { CartHasRecipeUpdateComponent } from './cart-has-recipe-update.component';

@Injectable({ providedIn: 'root' })
export class CartHasRecipeResolve implements Resolve<ICartHasRecipe> {
  constructor(private service: CartHasRecipeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICartHasRecipe> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cartHasRecipe: HttpResponse<CartHasRecipe>) => {
          if (cartHasRecipe.body) {
            return of(cartHasRecipe.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CartHasRecipe());
  }
}

export const cartHasRecipeRoute: Routes = [
  {
    path: '',
    component: CartHasRecipeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'shoppedApp.cartHasRecipe.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CartHasRecipeDetailComponent,
    resolve: {
      cartHasRecipe: CartHasRecipeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.cartHasRecipe.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CartHasRecipeUpdateComponent,
    resolve: {
      cartHasRecipe: CartHasRecipeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.cartHasRecipe.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CartHasRecipeUpdateComponent,
    resolve: {
      cartHasRecipe: CartHasRecipeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.cartHasRecipe.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
