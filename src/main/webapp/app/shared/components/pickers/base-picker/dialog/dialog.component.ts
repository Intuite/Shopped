import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { DialogData } from '../panel/panel.component';

@Component({
  selector: 'jhi-base-picker-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.scss'],
})
export class DialogComponent implements OnInit {
  searchText = '';
  options: any[] = [];
  initialSelections: any[] = [];
  newSelections: any[] = [];

  constructor(public dialogRef: MatDialogRef<DialogComponent>, @Inject(MAT_DIALOG_DATA) public data: DialogData) {}

  ngOnInit(): void {
    this.newSelections = [];
    this.initialSelections = this.data.selections;
    this.options = this._filterSelections(this.data.options, this.initialSelections);
  }

  addTag(rt: any): void {
    this.newSelections.push(rt);
    this.options = this._filterSelections(this.options, this.newSelections);
  }

  getSelections(): any[] {
    return this.initialSelections.concat(this.newSelections);
  }

  private _filterSelections(a: any[], b: any[]): any[] {
    const duplicates = this._getDuplicates(a, b);
    return a.filter(x => {
      for (const dup of duplicates) {
        if (dup.id === x.id) {
          return false;
        }
      }
      return true;
    });
  }

  private _getDuplicates(a: any[], b: any[]): any[] {
    const lookup = a.concat(b).reduce((c, d) => {
      if (d.id !== undefined) {
        c[d.id] = ++c[d.id] || 0;
      }
      return c;
    }, {});
    return a.filter(e => {
      if (e.id !== undefined) {
        return lookup[e.id];
      }
    });
  }
}
