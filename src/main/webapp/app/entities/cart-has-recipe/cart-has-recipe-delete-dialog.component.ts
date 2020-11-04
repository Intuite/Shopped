import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICartHasRecipe } from 'app/shared/model/cart-has-recipe.model';
import { CartHasRecipeService } from './cart-has-recipe.service';

@Component({
  templateUrl: './cart-has-recipe-delete-dialog.component.html',
})
export class CartHasRecipeDeleteDialogComponent {
  cartHasRecipe?: ICartHasRecipe;

  constructor(
    protected cartHasRecipeService: CartHasRecipeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cartHasRecipeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('cartHasRecipeListModification');
      this.activeModal.close();
    });
  }
}
