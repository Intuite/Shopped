import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICartHasIngredient } from 'app/shared/model/cart-has-ingredient.model';
import { ICartIngredient } from 'app/shared/model/cart-ingredient.model';

type EntityResponseType = HttpResponse<ICartHasIngredient>;
type EntityArrayResponseType = HttpResponse<ICartHasIngredient[]>;

@Injectable({ providedIn: 'root' })
export class CartHasIngredientService {
  public resourceUrl = SERVER_API_URL + 'api/cart-has-ingredients';

  constructor(protected http: HttpClient) {}

  create(cartHasIngredient: ICartHasIngredient): Observable<EntityResponseType> {
    return this.http.post<ICartHasIngredient>(this.resourceUrl, cartHasIngredient, { observe: 'response' });
  }

  update(cartHasIngredient: ICartHasIngredient): Observable<EntityResponseType> {
    return this.http.put<ICartHasIngredient>(this.resourceUrl, cartHasIngredient, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICartHasIngredient>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICartHasIngredient[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findByCart(cartId: number): Observable<EntityArrayResponseType> {
    const options = createRequestOption({ ...{ 'cartId.equals': cartId } });
    return this.http.get<ICartHasIngredient[]>(this.resourceUrl, {
      params: options,
      observe: 'response',
    });
  }

  map(i: ICartIngredient): ICartHasIngredient {
    return {
      id: i.cartHasIngredientId,
      amount: i.amount,
      status: i.status,
      cartId: i.cartId,
      ingredientName: i.name,
      ingredientId: i.id,
    };
  }
}
