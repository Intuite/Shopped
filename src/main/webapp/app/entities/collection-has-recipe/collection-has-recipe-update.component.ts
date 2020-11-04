import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICollectionHasRecipe, CollectionHasRecipe } from 'app/shared/model/collection-has-recipe.model';
import { CollectionHasRecipeService } from './collection-has-recipe.service';
import { ICollection } from 'app/shared/model/collection.model';
import { CollectionService } from 'app/entities/collection/collection.service';
import { IRecipe } from 'app/shared/model/recipe.model';
import { RecipeService } from 'app/entities/recipe/recipe.service';

type SelectableEntity = ICollection | IRecipe;

@Component({
  selector: 'jhi-collection-has-recipe-update',
  templateUrl: './collection-has-recipe-update.component.html',
})
export class CollectionHasRecipeUpdateComponent implements OnInit {
  isSaving = false;
  collections: ICollection[] = [];
  recipes: IRecipe[] = [];

  editForm = this.fb.group({
    id: [],
    status: [],
    collectionId: [null, Validators.required],
    recipeId: [null, Validators.required],
  });

  constructor(
    protected collectionHasRecipeService: CollectionHasRecipeService,
    protected collectionService: CollectionService,
    protected recipeService: RecipeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ collectionHasRecipe }) => {
      this.updateForm(collectionHasRecipe);

      this.collectionService.query().subscribe((res: HttpResponse<ICollection[]>) => (this.collections = res.body || []));

      this.recipeService.query().subscribe((res: HttpResponse<IRecipe[]>) => (this.recipes = res.body || []));
    });
  }

  updateForm(collectionHasRecipe: ICollectionHasRecipe): void {
    this.editForm.patchValue({
      id: collectionHasRecipe.id,
      status: collectionHasRecipe.status,
      collectionId: collectionHasRecipe.collectionId,
      recipeId: collectionHasRecipe.recipeId,
    });
  }

  previousState(): void {
    window.history.back();
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
      id: this.editForm.get(['id'])!.value,
      status: this.editForm.get(['status'])!.value,
      collectionId: this.editForm.get(['collectionId'])!.value,
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
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
