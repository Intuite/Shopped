import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRecipe } from 'app/shared/model/recipe.model';
import { RecipeService } from './recipe.service';
import { Status } from 'app/shared/model/enumerations/status.model';

@Component({
  templateUrl: './recipe-delete-dialog.component.html',
})
export class RecipeDeleteDialogComponent {
  recipe?: IRecipe;

  constructor(protected recipeService: RecipeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(element: IRecipe, newStatus: boolean): void {
    this.recipeService
      .update({
        ...element,
        status: !newStatus ? (Status.ACTIVE.toUpperCase() as Status) : (Status.INACTIVE.toUpperCase() as Status),
      })
      .subscribe(() => {
        this.activeModal.close();
      });
  }
}
