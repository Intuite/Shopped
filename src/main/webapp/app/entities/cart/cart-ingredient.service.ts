import { HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ICartHasIngredient } from 'app/shared/model/cart-has-ingredient.model';
import { IIngredient } from 'app/shared/model/ingredient.model';
import { CartHasIngredientService } from 'app/entities/cart-has-ingredient/cart-has-ingredient.service';
import { IngredientService } from 'app/entities/ingredient/ingredient.service';
import { ICartIngredient } from 'app/shared/model/cart-ingredient.model';
import { Status } from 'app/shared/model/enumerations/status.model';

@Injectable({
  providedIn: 'root',
})
export class CartIngredientService {
  ci: ICartIngredient[] = [];

  constructor(private chiService: CartHasIngredientService, private iService: IngredientService) {}

  create(ci: ICartIngredient): Observable<HttpResponse<ICartHasIngredient>> {
    return this.chiService.create(this.unmap(ci));
  }

  update(ci: ICartIngredient): Observable<HttpResponse<ICartHasIngredient>> {
    const chi = this.unmap(ci);
    return this.chiService.update(chi);
  }

  toggleStatus(ci: ICartIngredient): [ICartIngredient, Observable<HttpResponse<ICartHasIngredient>>] {
    ci.status =
      ci.status !== Status.PENDING.toUpperCase() ? (Status.PENDING.toUpperCase() as Status) : (Status.ACTIVE.toUpperCase() as Status);
    return [ci, this.chiService.update(this.unmap(ci))];
  }

  delete(ci: ICartIngredient): Observable<HttpResponse<ICartHasIngredient>> | null {
    if (ci.cartHasIngredientId !== undefined) {
      return this.chiService.delete(ci.cartHasIngredientId);
    }
    return null;
  }

  map(i: IIngredient, chi: ICartHasIngredient): ICartIngredient {
    return {
      id: i.id,
      name: i.name,
      amount: chi.amount,
      image: i.image,
      imageContentType: i.imageContentType,
      unitAbbrev: i.unitAbbrev,
      cartId: chi.cartId,
      cartHasIngredientId: chi.id,
      status: chi.status,
    };
  }

  unmap(ci: ICartIngredient): ICartHasIngredient {
    return {
      id: ci.cartHasIngredientId,
      amount: ci.amount,
      status: ci.status,
      cartId: ci.cartId,
      ingredientName: ci.name,
      ingredientId: ci.id,
    };
  }
}
