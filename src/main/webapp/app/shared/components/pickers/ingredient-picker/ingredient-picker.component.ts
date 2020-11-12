import { Component, OnDestroy, OnInit } from '@angular/core';
import { IIngredient } from 'app/shared/model/ingredient.model';
import { MatDialog } from '@angular/material/dialog';
import { MatListOption } from '@angular/material/list';
import { FormArray, FormGroup } from '@angular/forms';
import { IngredientService } from 'app/entities/ingredient/ingredient.service';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { IngredientPickerFormService } from 'app/shared/components/pickers/ingredient-picker/ingredient-picker-form.service';
import { DialogComponent } from 'app/shared/components/pickers/ingredient-picker/dialog/dialog.component';

@Component({
  selector: 'jhi-ingredient-picker',
  templateUrl: './ingredient-picker.component.html',
  styleUrls: ['./ingredient-picker.component.scss'],
})
export class IngredientPickerComponent implements OnInit, OnDestroy {
  // Ingredients for dialog
  ingredients!: IIngredient[];
  selectedList: IIngredient[] = [];
  // Form attributes
  ingredientForm!: FormGroup;
  ingredientFormSub!: Subscription;
  ingredientsSelected!: FormArray;
  formInvalid = false;
  // Expansion
  panelOpenState = false;

  constructor(
    private ingredientService: IngredientService,
    private ingredientPFService: IngredientPickerFormService,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.ingredientFormSub = this.ingredientPFService.form$.subscribe(ingredientsPicker => {
      this.ingredientForm = ingredientsPicker;
      this.ingredientsSelected = this.ingredientForm.get('selections') as FormArray;
    });
    this.ingredientService.query().subscribe(
      (res: HttpResponse<IIngredient[]>) => this.onSuccess(res.body),
      () => this.onError()
    );
  }

  getSelectedControls(): FormGroup[] {
    return this.ingredientsSelected.controls as FormGroup[];
  }

  ngOnDestroy(): void {
    this.ingredientFormSub.unsubscribe();
  }

  /*
   * Opens the dialog and shows ingredients remaining
   * */
  addIngredient(): void {
    const dialogRef = this.dialog.open(DialogComponent, {
      minWidth: '60%',
      panelClass: 'picker-dialog',
      data: this._filterSelections(),
    });
    dialogRef.afterClosed().subscribe(
      (result: MatListOption[]) => {
        if (result !== undefined) {
          this.selectedList = this.selectedList.concat(result.map((item: MatListOption) => item.value));
          this.ingredientPFService.setIngredients(this.selectedList);
        }
      },
      error => {
        console.warn(error);
      }
    );
  }

  /*
   * Removes the ingredient from the selected list
   * */
  deleteIngredient(index: number): void {
    this.ingredientPFService.deleteIngredient(index);
    this.selectedList.splice(index, 1);
  }

  saveIngredients(): void {
    console.warn('Ingredients saved!');
    console.warn(this.ingredientForm.value);
  }

  protected onSuccess(data: IIngredient[] | null): void {
    this.ingredients = data || [];
  }

  protected onError(): void {
    console.warn('There was an error');
  }

  private _filterSelections(): IIngredient[] {
    const duplicates = this._getDuplicates();
    return this.ingredients.filter(x => {
      for (const dup of duplicates) {
        if (dup.id === x.id) {
          return false;
        }
      }
      return true;
    });
  }

  private _getDuplicates(): IIngredient[] {
    const lookup = this.ingredients.concat(this.selectedList).reduce((a, e) => {
      if (e.id !== undefined) {
        a[e.id] = ++a[e.id] || 0;
      }
      return a;
    }, {});
    return this.ingredients.filter(e => {
      if (e.id !== undefined) {
        return lookup[e.id];
      }
    });
  }
}
