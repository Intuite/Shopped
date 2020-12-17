import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { IngredientPickerComponent } from 'app/shared/components/pickers/ingredient-picker/ingredient-picker.component';
import { CurrentCartService } from 'app/entities/cart/current-cart.service';

@Component({
  selector: 'jhi-add-ingredients',
  templateUrl: './add-ingredients.component.html',
  styleUrls: ['./add-ingredients.component.scss'],
})
export class AddIngredientsComponent implements OnInit {
  @ViewChild('ingredientPicker') ingredientPicker!: IngredientPickerComponent;

  constructor(public dialogRef: MatDialogRef<AddIngredientsComponent>, public service: CurrentCartService) {}

  ngOnInit(): void {}

  addIngredientsToCart(): void {
    this.service.addIngredients(this.ingredientPicker.getCartIngredients());
    this.dialogRef.close();
  }
}
