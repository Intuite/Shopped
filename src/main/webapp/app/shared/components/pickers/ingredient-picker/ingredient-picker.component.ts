import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { BehaviorSubject } from 'rxjs';
import { IIngredient } from 'app/shared/model/ingredient.model';
import { IngredientService } from 'app/entities/ingredient/ingredient.service';
import { CartIngredient } from 'app/shared/model/cart-ingredient.model';
import { Status } from 'app/shared/model/enumerations/status.model';

@Component({
  selector: 'jhi-ingredient-picker',
  templateUrl: './ingredient-picker.component.html',
  styleUrls: ['./ingredient-picker.component.scss'],
})
export class IngredientPickerComponent implements OnInit {
  reloadTagList$ = new BehaviorSubject<boolean>(true);
  ingredients!: IIngredient[];
  form?: FormGroup;

  constructor(public service: IngredientService) {}

  ngOnInit(): void {
    this.reloadTagList$.subscribe(() => {
      this.service
        .queryAll({
          ...{ 'status.equals': 'ACTIVE' },
        })
        .subscribe((response: any) => {
          this.ingredients = response.body.sort((a: IIngredient, b: IIngredient) => {
            return a.id !== undefined && b.id !== undefined ? (a.id > b.id ? 1 : -1) : -1;
          });
        });
    });
  }

  getIngredients(): any[] {
    return this.form !== undefined ? this.form.value.selections : [];
  }

  getCartIngredients(): CartIngredient[] {
    const ingredientList = this.getIngredients();
    const ciList: CartIngredient[] = [];
    ingredientList.forEach(item => {
      ciList.push({
        id: item.ingredient.id,
        name: item.ingredient.name,
        amount: item.amount,
        imageContentType: item.ingredient.imageContentType,
        image: item.ingredient.image,
        unitAbbrev: item.ingredient.unitAbbrev,
        cartHasIngredientId: undefined,
        status: Status.PENDING.toUpperCase() as Status,
      });
    });
    return ciList;
  }
}
