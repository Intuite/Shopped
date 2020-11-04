import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICartHasRecipe, CartHasRecipe } from 'app/shared/model/cart-has-recipe.model';
import { CartHasRecipeService } from './cart-has-recipe.service';
import { IRecipe } from 'app/shared/model/recipe.model';
import { RecipeService } from 'app/entities/recipe/recipe.service';
import { ICart } from 'app/shared/model/cart.model';
import { CartService } from 'app/entities/cart/cart.service';

type SelectableEntity = IRecipe | ICart;

@Component({
  selector: 'jhi-cart-has-recipe-update',
  templateUrl: './cart-has-recipe-update.component.html',
})
export class CartHasRecipeUpdateComponent implements OnInit {
  isSaving = false;
  recipes: IRecipe[] = [];
  carts: ICart[] = [];

  editForm = this.fb.group({
    id: [],
    status: [],
    recipeId: [null, Validators.required],
    cartId: [null, Validators.required],
  });

  constructor(
    protected cartHasRecipeService: CartHasRecipeService,
    protected recipeService: RecipeService,
    protected cartService: CartService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cartHasRecipe }) => {
      this.updateForm(cartHasRecipe);

      this.recipeService.query().subscribe((res: HttpResponse<IRecipe[]>) => (this.recipes = res.body || []));

      this.cartService.query().subscribe((res: HttpResponse<ICart[]>) => (this.carts = res.body || []));
    });
  }

  updateForm(cartHasRecipe: ICartHasRecipe): void {
    this.editForm.patchValue({
      id: cartHasRecipe.id,
      status: cartHasRecipe.status,
      recipeId: cartHasRecipe.recipeId,
      cartId: cartHasRecipe.cartId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cartHasRecipe = this.createFromForm();
    if (cartHasRecipe.id !== undefined) {
      this.subscribeToSaveResponse(this.cartHasRecipeService.update(cartHasRecipe));
    } else {
      this.subscribeToSaveResponse(this.cartHasRecipeService.create(cartHasRecipe));
    }
  }

  private createFromForm(): ICartHasRecipe {
    return {
      ...new CartHasRecipe(),
      id: this.editForm.get(['id'])!.value,
      status: this.editForm.get(['status'])!.value,
      recipeId: this.editForm.get(['recipeId'])!.value,
      cartId: this.editForm.get(['cartId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICartHasRecipe>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
