import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { BehaviorSubject } from 'rxjs';
import { IIngredient } from 'app/shared/model/ingredient.model';
import { IngredientService } from 'app/entities/ingredient/ingredient.service';

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
}
