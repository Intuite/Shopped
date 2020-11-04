import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICartHasIngredient, CartHasIngredient } from 'app/shared/model/cart-has-ingredient.model';
import { CartHasIngredientService } from './cart-has-ingredient.service';
import { CartHasIngredientComponent } from './cart-has-ingredient.component';
import { CartHasIngredientDetailComponent } from './cart-has-ingredient-detail.component';
import { CartHasIngredientUpdateComponent } from './cart-has-ingredient-update.component';

@Injectable({ providedIn: 'root' })
export class CartHasIngredientResolve implements Resolve<ICartHasIngredient> {
  constructor(private service: CartHasIngredientService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICartHasIngredient> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cartHasIngredient: HttpResponse<CartHasIngredient>) => {
          if (cartHasIngredient.body) {
            return of(cartHasIngredient.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CartHasIngredient());
  }
}

export const cartHasIngredientRoute: Routes = [
  {
    path: '',
    component: CartHasIngredientComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'shoppedApp.cartHasIngredient.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CartHasIngredientDetailComponent,
    resolve: {
      cartHasIngredient: CartHasIngredientResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.cartHasIngredient.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CartHasIngredientUpdateComponent,
    resolve: {
      cartHasIngredient: CartHasIngredientResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.cartHasIngredient.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CartHasIngredientUpdateComponent,
    resolve: {
      cartHasIngredient: CartHasIngredientResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'shoppedApp.cartHasIngredient.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
