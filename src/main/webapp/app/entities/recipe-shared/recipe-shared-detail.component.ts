import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRecipeShared } from 'app/shared/model/recipe-shared.model';

@Component({
  selector: 'jhi-recipe-shared-detail',
  templateUrl: './recipe-shared-detail.component.html',
})
export class RecipeSharedDetailComponent implements OnInit {
  recipeShared: IRecipeShared | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recipeShared }) => (this.recipeShared = recipeShared));
  }

  previousState(): void {
    window.history.back();
  }
}
