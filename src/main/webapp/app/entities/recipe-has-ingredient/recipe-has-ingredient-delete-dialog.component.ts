import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRecipeHasIngredient } from 'app/shared/model/recipe-has-ingredient.model';
import { RecipeHasIngredientService } from './recipe-has-ingredient.service';

@Component({
  templateUrl: './recipe-has-ingredient-delete-dialog.component.html',
})
export class RecipeHasIngredientDeleteDialogComponent {
  recipeHasIngredient?: IRecipeHasIngredient;

  constructor(
    protected recipeHasIngredientService: RecipeHasIngredientService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.recipeHasIngredientService.delete(id).subscribe(() => {
      this.eventManager.broadcast('recipeHasIngredientListModification');
      this.activeModal.close();
    });
  }
}
