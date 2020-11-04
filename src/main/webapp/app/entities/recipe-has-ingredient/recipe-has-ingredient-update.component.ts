import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRecipeHasIngredient, RecipeHasIngredient } from 'app/shared/model/recipe-has-ingredient.model';
import { RecipeHasIngredientService } from './recipe-has-ingredient.service';
import { IIngredient } from 'app/shared/model/ingredient.model';
import { IngredientService } from 'app/entities/ingredient/ingredient.service';
import { IRecipe } from 'app/shared/model/recipe.model';
import { RecipeService } from 'app/entities/recipe/recipe.service';

type SelectableEntity = IIngredient | IRecipe;

@Component({
  selector: 'jhi-recipe-has-ingredient-update',
  templateUrl: './recipe-has-ingredient-update.component.html',
})
export class RecipeHasIngredientUpdateComponent implements OnInit {
  isSaving = false;
  ingredients: IIngredient[] = [];
  recipes: IRecipe[] = [];

  editForm = this.fb.group({
    id: [],
    amount: [null, [Validators.required, Validators.min(0)]],
    status: [],
    ingredientId: [null, Validators.required],
    recipeId: [null, Validators.required],
  });

  constructor(
    protected recipeHasIngredientService: RecipeHasIngredientService,
    protected ingredientService: IngredientService,
    protected recipeService: RecipeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recipeHasIngredient }) => {
      this.updateForm(recipeHasIngredient);

      this.ingredientService.query().subscribe((res: HttpResponse<IIngredient[]>) => (this.ingredients = res.body || []));

      this.recipeService.query().subscribe((res: HttpResponse<IRecipe[]>) => (this.recipes = res.body || []));
    });
  }

  updateForm(recipeHasIngredient: IRecipeHasIngredient): void {
    this.editForm.patchValue({
      id: recipeHasIngredient.id,
      amount: recipeHasIngredient.amount,
      status: recipeHasIngredient.status,
      ingredientId: recipeHasIngredient.ingredientId,
      recipeId: recipeHasIngredient.recipeId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const recipeHasIngredient = this.createFromForm();
    if (recipeHasIngredient.id !== undefined) {
      this.subscribeToSaveResponse(this.recipeHasIngredientService.update(recipeHasIngredient));
    } else {
      this.subscribeToSaveResponse(this.recipeHasIngredientService.create(recipeHasIngredient));
    }
  }

  private createFromForm(): IRecipeHasIngredient {
    return {
      ...new RecipeHasIngredient(),
      id: this.editForm.get(['id'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      status: this.editForm.get(['status'])!.value,
      ingredientId: this.editForm.get(['ingredientId'])!.value,
      recipeId: this.editForm.get(['recipeId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecipeHasIngredient>>): void {
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
