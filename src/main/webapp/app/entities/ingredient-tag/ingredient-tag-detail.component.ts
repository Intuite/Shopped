import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIngredientTag } from 'app/shared/model/ingredient-tag.model';

@Component({
  selector: 'jhi-ingredient-tag-detail',
  templateUrl: './ingredient-tag-detail.component.html',
})
export class IngredientTagDetailComponent implements OnInit {
  ingredientTag: IIngredientTag | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ingredientTag }) => (this.ingredientTag = ingredientTag));
  }

  previousState(): void {
    window.history.back();
  }
}
