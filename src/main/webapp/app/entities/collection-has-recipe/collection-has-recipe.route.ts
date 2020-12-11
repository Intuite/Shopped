import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';
import { ICollectionHasRecipe, CollectionHasRecipe } from 'app/shared/model/collection-has-recipe.model';
import { CollectionHasRecipeService } from './collection-has-recipe.service';

@Injectable({ providedIn: 'root' })
export class CollectionHasRecipeResolve implements Resolve<ICollectionHasRecipe> {
  constructor(private service: CollectionHasRecipeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICollectionHasRecipe> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((collectionHasRecipe: HttpResponse<CollectionHasRecipe>) => {
          if (collectionHasRecipe.body) {
            return of(collectionHasRecipe.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CollectionHasRecipe());
  }
}

export const collectionHasRecipeRoute: Routes = [
  // {
  //   path: '',
  //   component: CollectionHasRecipeComponent,
  //   data: {
  //     authorities: [Authority.USER],
  //     defaultSort: 'id,asc',
  //     pageTitle: 'shoppedApp.collectionHasRecipe.home.title',
  //   },
  //   canActivate: [UserRouteAccessService],
  // },
  // {
  //   path: ':id/view',
  //   component: CollectionHasRecipeDetailComponent,
  //   resolve: {
  //     collectionHasRecipe: CollectionHasRecipeResolve,
  //   },
  //   data: {
  //     authorities: [Authority.USER],
  //     pageTitle: 'shoppedApp.collectionHasRecipe.home.title',
  //   },
  //   canActivate: [UserRouteAccessService],
  // },
  // {
  //   path: 'new',
  //   component: CollectionHasRecipeUpdateComponent,
  //   resolve: {
  //     collectionHasRecipe: CollectionHasRecipeResolve,
  //   },
  //   data: {
  //     authorities: [Authority.USER],
  //     pageTitle: 'shoppedApp.collectionHasRecipe.home.title',
  //   },
  //   canActivate: [UserRouteAccessService],
  // },
  // {
  //   path: ':id/edit',
  //   component: CollectionHasRecipeUpdateComponent,
  //   resolve: {
  //     collectionHasRecipe: CollectionHasRecipeResolve,
  //   },
  //   data: {
  //     authorities: [Authority.USER],
  //     pageTitle: 'shoppedApp.collectionHasRecipe.home.title',
  //   },
  //   canActivate: [UserRouteAccessService],
  // },
];
