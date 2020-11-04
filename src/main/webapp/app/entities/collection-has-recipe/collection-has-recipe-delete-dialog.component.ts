import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICollectionHasRecipe } from 'app/shared/model/collection-has-recipe.model';
import { CollectionHasRecipeService } from './collection-has-recipe.service';

@Component({
  templateUrl: './collection-has-recipe-delete-dialog.component.html',
})
export class CollectionHasRecipeDeleteDialogComponent {
  collectionHasRecipe?: ICollectionHasRecipe;

  constructor(
    protected collectionHasRecipeService: CollectionHasRecipeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.collectionHasRecipeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('collectionHasRecipeListModification');
      this.activeModal.close();
    });
  }
}
