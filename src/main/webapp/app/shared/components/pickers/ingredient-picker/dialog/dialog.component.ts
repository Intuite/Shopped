import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { IIngredient } from 'app/shared/model/ingredient.model';
import { MatSelectionList } from '@angular/material/list';

@Component({
  selector: 'jhi-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.scss'],
})
export class DialogComponent implements OnInit {
  @ViewChild('ingredientList') ingredientList!: MatSelectionList;

  constructor(public dialogRef: MatDialogRef<DialogComponent>, @Inject(MAT_DIALOG_DATA) public data: IIngredient[]) {}

  ngOnInit(): void {}

  exit(): void {
    this.dialogRef.close();
  }
}
