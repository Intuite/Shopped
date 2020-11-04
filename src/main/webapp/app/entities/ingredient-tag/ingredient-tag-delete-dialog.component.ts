import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIngredientTag } from 'app/shared/model/ingredient-tag.model';
import { IngredientTagService } from './ingredient-tag.service';

@Component({
  templateUrl: './ingredient-tag-delete-dialog.component.html',
})
export class IngredientTagDeleteDialogComponent {
  ingredientTag?: IIngredientTag;

  constructor(
    protected ingredientTagService: IngredientTagService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ingredientTagService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ingredientTagListModification');
      this.activeModal.close();
    });
  }
}
