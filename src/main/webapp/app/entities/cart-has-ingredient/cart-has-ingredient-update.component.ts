import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICartHasIngredient, CartHasIngredient } from 'app/shared/model/cart-has-ingredient.model';
import { CartHasIngredientService } from './cart-has-ingredient.service';
import { ICart } from 'app/shared/model/cart.model';
import { CartService } from 'app/entities/cart/cart.service';
import { IIngredient } from 'app/shared/model/ingredient.model';
import { IngredientService } from 'app/entities/ingredient/ingredient.service';

type SelectableEntity = ICart | IIngredient;

@Component({
  selector: 'jhi-cart-has-ingredient-update',
  templateUrl: './cart-has-ingredient-update.component.html',
})
export class CartHasIngredientUpdateComponent implements OnInit {
  isSaving = false;
  carts: ICart[] = [];
  ingredients: IIngredient[] = [];

  editForm = this.fb.group({
    id: [],
    amount: [null, [Validators.required, Validators.min(0)]],
    status: [],
    cartId: [null, Validators.required],
    ingredientId: [null, Validators.required],
  });

  constructor(
    protected cartHasIngredientService: CartHasIngredientService,
    protected cartService: CartService,
    protected ingredientService: IngredientService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cartHasIngredient }) => {
      this.updateForm(cartHasIngredient);

      this.cartService.query().subscribe((res: HttpResponse<ICart[]>) => (this.carts = res.body || []));

      this.ingredientService.query().subscribe((res: HttpResponse<IIngredient[]>) => (this.ingredients = res.body || []));
    });
  }

  updateForm(cartHasIngredient: ICartHasIngredient): void {
    this.editForm.patchValue({
      id: cartHasIngredient.id,
      amount: cartHasIngredient.amount,
      status: cartHasIngredient.status,
      cartId: cartHasIngredient.cartId,
      ingredientId: cartHasIngredient.ingredientId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cartHasIngredient = this.createFromForm();
    if (cartHasIngredient.id !== undefined) {
      this.subscribeToSaveResponse(this.cartHasIngredientService.update(cartHasIngredient));
    } else {
      this.subscribeToSaveResponse(this.cartHasIngredientService.create(cartHasIngredient));
    }
  }

  private createFromForm(): ICartHasIngredient {
    return {
      ...new CartHasIngredient(),
      id: this.editForm.get(['id'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      status: this.editForm.get(['status'])!.value,
      cartId: this.editForm.get(['cartId'])!.value,
      ingredientId: this.editForm.get(['ingredientId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICartHasIngredient>>): void {
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
