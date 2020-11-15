import { Component, Inject, OnInit } from '@angular/core';

import { IIngredientTag } from 'app/shared/model/ingredient-tag.model';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'jhi-ingredient-tag-detail',
  templateUrl: './ingredient-tag-detail.component.html',
})
export class IngredientTagDetailComponent implements OnInit {
  element: any;

  constructor(@Inject(MAT_DIALOG_DATA) public data: IIngredientTag) {}

  ngOnInit(): void {
    this.element = this.data;
  }

  previousState(): void {
    window.history.back();
  }
}
