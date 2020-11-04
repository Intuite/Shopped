import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRecipeTag } from 'app/shared/model/recipe-tag.model';
import { RecipeTagService } from './recipe-tag.service';

@Component({
  templateUrl: './recipe-tag-delete-dialog.component.html',
})
export class RecipeTagDeleteDialogComponent {
  recipeTag?: IRecipeTag;

  constructor(protected recipeTagService: RecipeTagService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.recipeTagService.delete(id).subscribe(() => {
      this.eventManager.broadcast('recipeTagListModification');
      this.activeModal.close();
    });
  }
}
