import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRecipeShared } from 'app/shared/model/recipe-shared.model';
import { RecipeSharedService } from './recipe-shared.service';

@Component({
  templateUrl: './recipe-shared-delete-dialog.component.html',
})
export class RecipeSharedDeleteDialogComponent {
  recipeShared?: IRecipeShared;

  constructor(
    protected recipeSharedService: RecipeSharedService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.recipeSharedService.delete(id).subscribe(() => {
      this.eventManager.broadcast('recipeSharedListModification');
      this.activeModal.close();
    });
  }
}
