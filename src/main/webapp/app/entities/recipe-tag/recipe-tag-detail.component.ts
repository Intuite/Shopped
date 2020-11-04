import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRecipeTag } from 'app/shared/model/recipe-tag.model';

@Component({
  selector: 'jhi-recipe-tag-detail',
  templateUrl: './recipe-tag-detail.component.html',
})
export class RecipeTagDetailComponent implements OnInit {
  recipeTag: IRecipeTag | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recipeTag }) => (this.recipeTag = recipeTag));
  }

  previousState(): void {
    window.history.back();
  }
}
