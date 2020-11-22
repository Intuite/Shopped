import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormGroup } from '@angular/forms';
import { IIngredient } from 'app/shared/model/ingredient.model';

@Component({
  selector: 'jhi-ingredient-selection-list',
  templateUrl: './selection-list.component.html',
  styleUrls: ['./selection-list.component.scss'],
})
export class SelectionListComponent implements OnInit {
  @Input() list!: FormArray;
  @Input() ingredientList!: IIngredient[];
  @Output() isEmpty = new EventEmitter<boolean>();
  controls!: FormGroup[];

  constructor() {}

  ngOnInit(): void {
    if (this.list !== null) this.controls = this.list.controls as FormGroup[];
  }

  remove(index: number): void {
    this.list.removeAt(index);
    this.ingredientList.splice(index, 1);
    if (this.list.length === 0) {
      this.isEmpty.emit(true);
    }
    this.isEmpty.emit(false);
  }
}
