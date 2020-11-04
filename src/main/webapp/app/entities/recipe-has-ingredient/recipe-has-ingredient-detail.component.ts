import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRecipeHasIngredient } from 'app/shared/model/recipe-has-ingredient.model';

@Component({
  selector: 'jhi-recipe-has-ingredient-detail',
  templateUrl: './recipe-has-ingredient-detail.component.html',
})
export class RecipeHasIngredientDetailComponent implements OnInit {
  recipeHasIngredient: IRecipeHasIngredient | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recipeHasIngredient }) => (this.recipeHasIngredient = recipeHasIngredient));
  }

  previousState(): void {
    window.history.back();
  }
}
