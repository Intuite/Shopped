import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { Component, ElementRef, ViewChild, Input, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatAutocompleteSelectedEvent, MatAutocomplete } from '@angular/material/autocomplete';
import { MatChipInputEvent } from '@angular/material/chips';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Component({
  selector: 'jhi-chips',
  templateUrl: './chips.component.html',
  styleUrls: ['./chips.component.scss'],
})
export class ChipsComponent implements OnInit {
  visible = true;
  selectable = true;
  removable = true;
  separatorKeyCodes: number[] = [ENTER, COMMA];
  entityCtrl = new FormControl();
  filteredEntities: Observable<string[]>;
  entities: string[] = [];
  @Input() allEntities: string[] | undefined;
  @Input() entityName: string | undefined;

  @ViewChild('entityInput') entityInput: ElementRef<HTMLInputElement> | undefined;
  @ViewChild('auto') matAutocomplete: MatAutocomplete | undefined;

  constructor() {
    this.filteredEntities = this.entityCtrl.valueChanges.pipe(
      map((entity: string | null) =>
        entity ? this._filter(entity) : this.allEntities ? this.allEntities.slice() : (this.allEntities = [])
      )
    );
  }

  ngOnInit(): void {}

  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    // Add our entity
    if ((value || '').trim()) {
      this.entities.push(value.trim());
    }

    // Reset the input value
    if (input) {
      input.value = '';
    }

    this.entityCtrl.setValue(null);
  }

  remove(entitie: string): void {
    const index = this.entities.indexOf(entitie);

    if (index >= 0) {
      this.entities.splice(index, 1);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    this.entities.push(event.option.viewValue);
    if (this.entityInput !== undefined) {
      this.entityInput.nativeElement.value = '';
    }
    this.entityCtrl.setValue(null);
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.allEntities ? this.allEntities.filter(entity => entity.toLowerCase().startsWith(filterValue)) : [];
  }
}
