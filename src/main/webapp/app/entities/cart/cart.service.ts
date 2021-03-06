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
import { ICartHasIngredient } from 'app/shared/model/cart-has-ingredient.model';
import { ICartIngredient } from 'app/shared/model/cart-ingredient.model';

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

  // TODO validar si la receta ya fue agregada previamente
  // TODO sumar los amounts de los ingredientes
  // TODO si la receta ya estaba, solo agregar los amounts y notificar a la persona cuantas veces ha sido agregada la receta al cart
  addRecipe(recipe: IRecipe, cartIngredients: ICartIngredient[], account: Account): void {
    this.query({ 'userId.equals': account.id, 'status.equals': 'ACTIVE' }).subscribe(response => {
      if (response.body !== null && response.body.length > 0)
        this.addCartHasRecipe(this.chrService.map(recipe, response.body[0].id), cartIngredients);
    });
  }

  launchSnackbar(msg: string, action: string, seconds: number): void {
    this.snackBar.open(msg, action, {
      duration: seconds * 1000,
      horizontalPosition: 'center',
      verticalPosition: 'bottom',
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

  private addCartHasRecipe(chr: ICartHasRecipe, cartIngredients: ICartIngredient[]): void {
    this.chrService.findByCart(chr.cartId!).subscribe(chrResponse => {
      const recipeList = chrResponse.body!;
      if (recipeList.length === 0) {
        this.chrService.create(chr).subscribe(res => {
          if (res.body !== null) {
            this.insertIngredientsFromRecipe(
              cartIngredients.map(x => {
                x.cartId = chr.cartId;
                return x;
              })
            );
          }
        });
      } else {
        const equals = recipeList.find(r => r.recipeName === chr.recipeName);
        if (equals !== undefined) this.launchSnackbar('You have this recipe in your cart', 'Thanks!', 4);
        else {
          this.chrService.create(chr).subscribe(res => {
            if (res.body !== null) {
              this.insertIngredientsFromRecipe(
                cartIngredients.map(x => {
                  x.cartId = chr.cartId;
                  return x;
                })
              );
            }
          });
        }
      }
    });
  }

  private insertIngredientsFromRecipe(cartIngredients: ICartIngredient[]): void {
    cartIngredients.forEach(ci => {
      const chi: ICartHasIngredient = {
        ingredientId: ci.id,
        ingredientName: ci.name,
        amount: ci.amount,
        cartId: ci.cartId!,
        status: Status.PENDING.toUpperCase() as Status,
      };
      this.chiService
        .query({
          'cartId.equals': ci.cartId,
          'ingredientId.equals': ci.id,
          'status.in': ['ACTIVE', 'PENDING'],
        })
        .subscribe(cartIngredientResponse => {
          const cibody = cartIngredientResponse.body;
          let obs: Observable<HttpResponse<ICartIngredient>>;
          if (cibody !== null && cibody[0] !== undefined) {
            const current = cibody[0];
            chi.amount = current.amount! + chi.amount!;
            chi.id = current.id;
            obs = this.chiService.update(chi);
          } else {
            obs = this.chiService.create(chi);
          }
          obs.subscribe(() => this.launchSnackbar('This recipe was added to your cart', 'Thanks!', 4));
        });
    });
  }

  private addCartHasIngredient(ing: IIngredient, cid: number | undefined): void {
    const chi = {
      amount: 1,
      status: Status.PENDING.toUpperCase() as Status,
      cartId: cid,
      ingredientName: ing.name,
      ingredientId: ing.id,
    };
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
              this.launchSnackbar(`1 ${ing.unitAbbrev} of ${ing.name} was added to your cart`, 'Thanks!', 3);
            }
          });
        } else {
          this.launchSnackbar('You already have that ingredient in your cart', 'Ok', 2);
        }
      });
  }
}
