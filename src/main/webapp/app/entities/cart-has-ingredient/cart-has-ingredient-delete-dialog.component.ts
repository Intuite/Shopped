import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICartHasIngredient } from 'app/shared/model/cart-has-ingredient.model';
import { CartHasIngredientService } from './cart-has-ingredient.service';

@Component({
  templateUrl: './cart-has-ingredient-delete-dialog.component.html',
})
export class CartHasIngredientDeleteDialogComponent {
  cartHasIngredient?: ICartHasIngredient;

  constructor(
    protected cartHasIngredientService: CartHasIngredientService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cartHasIngredientService.delete(id).subscribe(() => {
      this.eventManager.broadcast('cartHasIngredientListModification');
      this.activeModal.close();
    });
  }
}
