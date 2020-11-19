import { Injectable } from '@angular/core';
import {
  IngredientPicker,
  IngredientPKForm,
  IngredientPKSelection,
  IngredientPKSelectionForm,
} from 'app/shared/components/pickers/ingredient-picker/ingredient-picker.model';
import { BehaviorSubject, Observable } from 'rxjs';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { IIngredient } from 'app/shared/model/ingredient.model';

@Injectable({
  providedIn: 'root',
})
export class IngredientPickerFormService {
  private form: BehaviorSubject<FormGroup> = new BehaviorSubject(this.fb.group(new IngredientPKForm(new IngredientPicker([]))));
  form$: Observable<FormGroup> = this.form.asObservable();

  constructor(private fb: FormBuilder) {}

  addIngredient(ingredient: IIngredient): void {
    const currentForm = this.form.getValue();
    const currentSelected = currentForm.get('selections') as FormArray;
    currentSelected.push(this.fb.group(new IngredientPKSelectionForm(new IngredientPKSelection(1, ingredient))));
    this.form.next(currentForm);
  }

  setIngredients(ingredients: IIngredient[]): void {
    const currentForm = this.form.getValue();
    const currentSelected = currentForm.get('selections') as FormArray;
    currentSelected.clear();
    ingredients.forEach(x => {
      currentSelected.push(this.fb.group(new IngredientPKSelectionForm(new IngredientPKSelection(1, x))));
    });
    this.form.next(currentForm);
  }

  deleteIngredient(i: number): void {
    const currentForm = this.form.getValue();
    const currentSelected = currentForm.get('selections') as FormArray;
    currentSelected.removeAt(i);
    this.form.next(currentForm);
  }
}
