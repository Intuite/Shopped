import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICollectionHasRecipe } from 'app/shared/model/collection-has-recipe.model';

@Component({
  selector: 'jhi-collection-has-recipe-detail',
  templateUrl: './collection-has-recipe-detail.component.html',
})
export class CollectionHasRecipeDetailComponent implements OnInit {
  collectionHasRecipe: ICollectionHasRecipe | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ collectionHasRecipe }) => (this.collectionHasRecipe = collectionHasRecipe));
  }

  previousState(): void {
    window.history.back();
  }
}
