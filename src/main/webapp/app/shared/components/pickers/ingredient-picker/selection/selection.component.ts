import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { IIngredient } from 'app/shared/model/ingredient.model';

@Component({
  selector: 'jhi-ingredient-selection',
  templateUrl: './selection.component.html',
  styleUrls: ['./selection.component.scss'],
})
export class SelectionComponent implements OnInit {
  @Input() ingredientSelectedForm!: FormGroup;
  @Input() index!: number;
  @Output() deselect: EventEmitter<number> = new EventEmitter();
  ingredient!: IIngredient;

  constructor() {}

  ngOnInit(): void {
    const ing = this.ingredientSelectedForm.get('ingredient');
    this.ingredient = ing !== null ? ing.value : { name: 'null', unitAbbrev: 'null' };
  }

  deselectItem(): void {
    this.deselect.emit(this.index);
  }
}
