import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICartHasRecipe } from 'app/shared/model/cart-has-recipe.model';

@Component({
  selector: 'jhi-cart-has-recipe-detail',
  templateUrl: './cart-has-recipe-detail.component.html',
})
export class CartHasRecipeDetailComponent implements OnInit {
  cartHasRecipe: ICartHasRecipe | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cartHasRecipe }) => (this.cartHasRecipe = cartHasRecipe));
  }

  previousState(): void {
    window.history.back();
  }
}
