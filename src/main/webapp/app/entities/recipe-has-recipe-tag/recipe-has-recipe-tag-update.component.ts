import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRecipeHasRecipeTag, RecipeHasRecipeTag } from 'app/shared/model/recipe-has-recipe-tag.model';
import { RecipeHasRecipeTagService } from './recipe-has-recipe-tag.service';
import { IRecipe } from 'app/shared/model/recipe.model';
import { RecipeService } from 'app/entities/recipe/recipe.service';
import { IRecipeTag } from 'app/shared/model/recipe-tag.model';
import { RecipeTagService } from 'app/entities/recipe-tag/recipe-tag.service';

type SelectableEntity = IRecipe | IRecipeTag;

@Component({
  selector: 'jhi-recipe-has-recipe-tag-update',
  templateUrl: './recipe-has-recipe-tag-update.component.html',
})
export class RecipeHasRecipeTagUpdateComponent implements OnInit {
  isSaving = false;
  recipes: IRecipe[] = [];
  recipetags: IRecipeTag[] = [];

  editForm = this.fb.group({
    id: [],
    status: [],
    recipeId: [null, Validators.required],
    recipeTagId: [null, Validators.required],
  });

  constructor(
    protected recipeHasRecipeTagService: RecipeHasRecipeTagService,
    protected recipeService: RecipeService,
    protected recipeTagService: RecipeTagService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recipeHasRecipeTag }) => {
      this.updateForm(recipeHasRecipeTag);

      this.recipeService.query().subscribe((res: HttpResponse<IRecipe[]>) => (this.recipes = res.body || []));

      this.recipeTagService.query().subscribe((res: HttpResponse<IRecipeTag[]>) => (this.recipetags = res.body || []));
    });
  }

  updateForm(recipeHasRecipeTag: IRecipeHasRecipeTag): void {
    this.editForm.patchValue({
      id: recipeHasRecipeTag.id,
      status: recipeHasRecipeTag.status,
      recipeId: recipeHasRecipeTag.recipeId,
      recipeTagId: recipeHasRecipeTag.recipeTagId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const recipeHasRecipeTag = this.createFromForm();
    if (recipeHasRecipeTag.id !== undefined) {
      this.subscribeToSaveResponse(this.recipeHasRecipeTagService.update(recipeHasRecipeTag));
    } else {
      this.subscribeToSaveResponse(this.recipeHasRecipeTagService.create(recipeHasRecipeTag));
    }
  }

  private createFromForm(): IRecipeHasRecipeTag {
    return {
      ...new RecipeHasRecipeTag(),
      id: this.editForm.get(['id'])!.value,
      status: this.editForm.get(['status'])!.value,
      recipeId: this.editForm.get(['recipeId'])!.value,
      recipeTagId: this.editForm.get(['recipeTagId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecipeHasRecipeTag>>): void {
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
