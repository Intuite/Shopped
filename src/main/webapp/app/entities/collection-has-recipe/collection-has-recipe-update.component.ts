import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICollectionHasRecipe, CollectionHasRecipe } from 'app/shared/model/collection-has-recipe.model';
import { CollectionHasRecipeService } from './collection-has-recipe.service';
import { ICollection } from 'app/shared/model/collection.model';
import { IRecipe } from 'app/shared/model/recipe.model';
import { RecipeService } from 'app/entities/recipe/recipe.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { Status } from 'app/shared/model/enumerations/status.model';

type SelectableEntity = ICollection | IRecipe;

@Component({
  selector: 'jhi-collection-has-recipe-update',
  templateUrl: './collection-has-recipe-update.component.html',
})
export class CollectionHasRecipeUpdateComponent implements OnInit {
  isSaving = false;
  recipes: IRecipe[] = [];
  currentCollection?: ICollection;

  editForm = this.fb.group({
    recipeId: [null, Validators.required],
  });

  constructor(
    protected collectionHasRecipeService: CollectionHasRecipeService,
    protected recipeService: RecipeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  ngOnInit(): void {
    this.updateForm();
    this.recipeService.query().subscribe((res: HttpResponse<IRecipe[]>) => (this.recipes = res.body || []));
  }

  updateForm(): void {
    this.editForm.patchValue({
      recipeId: null,
    });
  }

  // previousState(): void {
  //   window.history.back();
  // }

  close(): void {
    this.activeModal.close();
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  save(): void {
    this.isSaving = true;
    const collectionHasRecipe = this.createFromForm();
    if (collectionHasRecipe.id !== undefined) {
      this.subscribeToSaveResponse(this.collectionHasRecipeService.update(collectionHasRecipe));
    } else {
      this.subscribeToSaveResponse(this.collectionHasRecipeService.create(collectionHasRecipe));
    }
  }

  private createFromForm(): ICollectionHasRecipe {
    return {
      ...new CollectionHasRecipe(),
      status: 'ACTIVE' as Status,
      collectionId: this.currentCollection?.id,
      recipeId: this.editForm.get(['recipeId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICollectionHasRecipe>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.eventManager.broadcast('collectionHasRecipeListModification');
    this.close();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
