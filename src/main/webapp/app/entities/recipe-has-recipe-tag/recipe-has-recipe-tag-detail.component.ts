import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRecipeHasRecipeTag } from 'app/shared/model/recipe-has-recipe-tag.model';

@Component({
  selector: 'jhi-recipe-has-recipe-tag-detail',
  templateUrl: './recipe-has-recipe-tag-detail.component.html',
})
export class RecipeHasRecipeTagDetailComponent implements OnInit {
  recipeHasRecipeTag: IRecipeHasRecipeTag | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recipeHasRecipeTag }) => (this.recipeHasRecipeTag = recipeHasRecipeTag));
  }

  previousState(): void {
    window.history.back();
  }
}
