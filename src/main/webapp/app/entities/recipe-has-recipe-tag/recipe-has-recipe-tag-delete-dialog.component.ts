import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRecipeHasRecipeTag } from 'app/shared/model/recipe-has-recipe-tag.model';
import { RecipeHasRecipeTagService } from './recipe-has-recipe-tag.service';

@Component({
  templateUrl: './recipe-has-recipe-tag-delete-dialog.component.html',
})
export class RecipeHasRecipeTagDeleteDialogComponent {
  recipeHasRecipeTag?: IRecipeHasRecipeTag;

  constructor(
    protected recipeHasRecipeTagService: RecipeHasRecipeTagService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.recipeHasRecipeTagService.delete(id).subscribe(() => {
      this.eventManager.broadcast('recipeHasRecipeTagListModification');
      this.activeModal.close();
    });
  }
}
