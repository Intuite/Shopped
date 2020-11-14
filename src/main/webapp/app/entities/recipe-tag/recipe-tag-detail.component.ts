import { Component, Inject, OnInit } from '@angular/core';

import { IRecipeTag } from 'app/shared/model/recipe-tag.model';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'jhi-recipe-tag-detail',
  templateUrl: './recipe-tag-detail.component.html',
})
export class RecipeTagDetailComponent implements OnInit {
  element: any;

  constructor(@Inject(MAT_DIALOG_DATA) public data: IRecipeTag) {}

  ngOnInit(): void {
    this.element = this.data;
  }

  previousState(): void {
    window.history.back();
  }
}
