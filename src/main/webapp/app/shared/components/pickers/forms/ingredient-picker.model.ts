import { IIngredient } from 'app/shared/model/ingredient.model';
import { FormArray, FormControl, Validators } from '@angular/forms';

// Total selections
export class IngredientPicker {
  selections: IngredientPKSelection[];

  constructor(selections: IngredientPKSelection[]) {
    this.selections = selections;
  }
}

// Complete Form
export class IngredientPKForm {
  selections = new FormArray([]);

  constructor(ip: IngredientPicker) {
    this.selections.setValue(ip.selections);
  }
}

// Selection
export class IngredientPKSelection {
  amount: number;
  ingredient: IIngredient;

  constructor(amount: number, ingredient: IIngredient) {
    this.amount = amount;
    this.ingredient = ingredient;
  }
}

// Selection Form
export class IngredientPKSelectionForm {
  amount = new FormControl();
  ingredient!: IIngredient;

  constructor(ip: IngredientPKSelection) {
    this.amount.setValue(ip.amount);
    this.amount.setValidators([Validators.required, Validators.min(1)]);
    this.ingredient = ip.ingredient;
  }
}
