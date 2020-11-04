import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIngredientHasIngredientTag } from 'app/shared/model/ingredient-has-ingredient-tag.model';

@Component({
  selector: 'jhi-ingredient-has-ingredient-tag-detail',
  templateUrl: './ingredient-has-ingredient-tag-detail.component.html',
})
export class IngredientHasIngredientTagDetailComponent implements OnInit {
  ingredientHasIngredientTag: IIngredientHasIngredientTag | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ingredientHasIngredientTag }) => (this.ingredientHasIngredientTag = ingredientHasIngredientTag));
  }

  previousState(): void {
    window.history.back();
  }
}
