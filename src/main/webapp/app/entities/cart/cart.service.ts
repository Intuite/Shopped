import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICart } from 'app/shared/model/cart.model';
import { IIngredient } from 'app/shared/model/ingredient.model';
import { CartHasIngredientService } from 'app/entities/cart-has-ingredient/cart-has-ingredient.service';
import { Status } from 'app/shared/model/enumerations/status.model';
import { Account } from 'app/core/user/account.model';
import { MatSnackBar } from '@angular/material/snack-bar';
import { IRecipe } from 'app/shared/model/recipe.model';
import { CartHasRecipeService } from 'app/entities/cart-has-recipe/cart-has-recipe.service';
import { RecipeHasIngredientService } from 'app/entities/recipe-has-ingredient/recipe-has-ingredient.service';
import { ICartHasRecipe } from 'app/shared/model/cart-has-recipe.model';

type EntityResponseType = HttpResponse<ICart>;
type EntityArrayResponseType = HttpResponse<ICart[]>;

@Injectable({ providedIn: 'root' })
export class CartService {
  public resourceUrl = SERVER_API_URL + 'api/carts';

  constructor(
    protected http: HttpClient,
    private chiService: CartHasIngredientService,
    private chrService: CartHasRecipeService,
    private rhiService: RecipeHasIngredientService,
    private snackBar: MatSnackBar
  ) {}

  create(cart: ICart): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cart);
    return this.http
      .post<ICart>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(cart: ICart): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cart);
    return this.http
      .put<ICart>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICart>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICart[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addIngredient(ing: IIngredient, a: Account): void {
    this.query({
      'userId.equals': a.id,
      'status.equals': 'ACTIVE',
    }).subscribe(cartResponse => {
      console.warn(cartResponse.body);
      if (cartResponse.body !== null && cartResponse.body.length > 0) {
        this.addCartHasIngredient(ing, cartResponse.body[0].id);
      } else {
        this.create({
          userLogin: a.login,
          userId: a.id,
        }).subscribe(createCartResponse => {
          if (createCartResponse.body !== null) {
            this.addCartHasIngredient(ing, createCartResponse.body.id);
          }
        });
      }
    });
  }

  addRecipe(recipe: IRecipe | null, account: Account): void {
    this.query({
      'userId.equals': account.id,
      'status.equals': 'ACTIVE',
    }).subscribe(cartResponse => {
      console.warn(cartResponse.body);
      if (cartResponse.body !== null && cartResponse.body.length > 0 && recipe !== null) {
        const chr: ICartHasRecipe = {
          cartId: cartResponse.body[0].id,
          recipeId: recipe.id,
          recipeName: recipe.name,
          status: Status.ACTIVE.toUpperCase() as Status,
        };
        this.chrService.create(chr).subscribe(res => {
          if (res.body !== null) {
            const rcp = res.body;
            this.rhiService.query({ 'recipeId.equals': chr.recipeName }).subscribe(rhi => {
              const recipeIngredients = rhi.body;
              recipeIngredients?.forEach(ri => {
                console.warn(ri);
              });
            });
          }
        });
      }
    });
  }

  protected convertDateFromClient(cart: ICart): ICart {
    const copy: ICart = Object.assign({}, cart, {
      created: cart.created && cart.created.isValid() ? cart.created.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.created = res.body.created ? moment(res.body.created) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((cart: ICart) => {
        cart.created = cart.created ? moment(cart.created) : undefined;
      });
    }
    return res;
  }

  private addCartHasIngredient(ing: IIngredient, cid: number | undefined): void {
    const chi = {
      amount: 1,
      status: Status.PENDING.toUpperCase() as Status,
      cartId: cid,
      ingredientName: ing.name,
      ingredientId: ing.id,
    };
    console.warn(chi);
    this.chiService
      .query({
        'ingredientId.equals': ing.id,
        'cartId.equals': cid,
        'status.in': ['PENDING', 'ACTIVE'],
      })
      .subscribe(exists => {
        console.warn(exists.body);
        if (exists.body !== null && exists.body.length === 0) {
          this.chiService.create(chi).subscribe(() => {
            if (chi) {
              this.snackBar.open(`1 ${ing.unitAbbrev} of ${ing.name} was added to your cart`, 'Thanks!', {
                duration: 4000,
                horizontalPosition: 'center',
                verticalPosition: 'bottom',
              });
            }
          });
        } else {
          this.snackBar.open('You already have that ingredient in your cart', 'Ok', {
            duration: 2000,
            horizontalPosition: 'center',
            verticalPosition: 'bottom',
          });
        }
      });
  }
}
