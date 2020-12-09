import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ICartIngredient } from 'app/shared/model/cart-ingredient.model';

@Component({
  selector: 'jhi-remove-ingredient',
  templateUrl: './remove-ingredient.component.html',
  styleUrls: ['./remove-ingredient.component.scss'],
})
export class RemoveIngredientComponent implements OnInit {
  constructor(@Inject(MAT_DIALOG_DATA) public data: ICartIngredient) {}

  ngOnInit(): void {}
}
