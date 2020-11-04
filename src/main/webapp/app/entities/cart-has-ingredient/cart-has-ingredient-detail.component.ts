import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICartHasIngredient } from 'app/shared/model/cart-has-ingredient.model';

@Component({
  selector: 'jhi-cart-has-ingredient-detail',
  templateUrl: './cart-has-ingredient-detail.component.html',
})
export class CartHasIngredientDetailComponent implements OnInit {
  cartHasIngredient: ICartHasIngredient | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cartHasIngredient }) => (this.cartHasIngredient = cartHasIngredient));
  }

  previousState(): void {
    window.history.back();
  }
}
