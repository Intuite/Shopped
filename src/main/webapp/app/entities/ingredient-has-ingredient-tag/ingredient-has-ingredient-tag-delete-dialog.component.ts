import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIngredientHasIngredientTag } from 'app/shared/model/ingredient-has-ingredient-tag.model';
import { IngredientHasIngredientTagService } from './ingredient-has-ingredient-tag.service';

@Component({
  templateUrl: './ingredient-has-ingredient-tag-delete-dialog.component.html',
})
export class IngredientHasIngredientTagDeleteDialogComponent {
  ingredientHasIngredientTag?: IIngredientHasIngredientTag;

  constructor(
    protected ingredientHasIngredientTagService: IngredientHasIngredientTagService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ingredientHasIngredientTagService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ingredientHasIngredientTagListModification');
      this.activeModal.close();
    });
  }
}
