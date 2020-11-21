import { Component, EventEmitter, Input, OnDestroy, OnInit, Output, ViewChild } from '@angular/core';
import { FormArray, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatExpansionPanel } from '@angular/material/expansion';
import { Subscription } from 'rxjs';
import { DialogComponent } from '../dialog/dialog.component';
import { IngredientPickerFormService } from '../ingredient-picker-form.service';

export interface DialogData {
  title: string;
  options: any[];
  selections: any[];
}

@Component({
  selector: 'jhi-ingredient-panel',
  templateUrl: './panel.component.html',
  styleUrls: ['./panel.component.scss'],
})
export class PanelComponent implements OnInit, OnDestroy {
  @Input() options!: any[];
  @Output() selectionOutput = new EventEmitter<FormGroup>();
  selections: any[] = [];

  @ViewChild('panel') panel!: MatExpansionPanel;
  panelStatus = false;

  // Form Attributes
  ingredientForm!: FormGroup;
  ingredientFormSub!: Subscription;
  ingredientsSelected!: FormArray;
  formInvalid = false;

  constructor(public ingredientPFService: IngredientPickerFormService, public dialog: MatDialog) {}

  ngOnInit(): void {
    this.ingredientFormSub = this.ingredientPFService.form$.subscribe(ingredientsPicker => {
      this.ingredientForm = ingredientsPicker;
      this.ingredientsSelected = this.ingredientForm.get('selections') as FormArray;
    });
  }

  ngOnDestroy(): void {
    this.ingredientFormSub.unsubscribe();
  }

  launchDialog(): void {
    const dialogRef = this.dialog.open(DialogComponent, {
      maxWidth: '1000px',
      maxHeight: '90%',
      width: '90%',
      data: {
        title: 'Ingredient Picker',
        options: this.options.sort((a: any, b: any) => {
          const x = a.name.toUpperCase(),
            y = b.name.toUpperCase();
          return x === y ? 0 : x > y ? 1 : -1;
        }),
        selections: this.selections,
      },
    });
    dialogRef.afterClosed().subscribe((selections: any[]) => {
      if (selections !== undefined && selections.length !== 0) {
        this.selections = selections;
        this.ingredientPFService.setIngredients(this.selections);
      } else this.close();
      this.selectionOutput.emit(this.ingredientForm);
    });
  }

  togglePanel(isEmpty: boolean): void {
    if (isEmpty) this.close();
  }

  checkSelections(): void {
    if (this.selections.length === 0) {
      this.close();
      this.launchDialog();
    } else {
      this.panelStatus = true;
    }
  }

  getSelectedForms(): FormArray {
    return this.ingredientsSelected;
  }

  private close(): void {
    this.panel.close();
    this.panelStatus = false;
  }
}
