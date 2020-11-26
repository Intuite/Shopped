import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatExpansionPanel } from '@angular/material/expansion';
import { DialogComponent } from '../dialog/dialog.component';

export interface DialogData {
  title: string;
  options: any[];
  selections: any[];
}

@Component({
  selector: 'jhi-base-picker-panel',
  templateUrl: './panel.component.html',
  styleUrls: ['./panel.component.scss'],
})
export class PanelComponent implements OnInit {
  @Input() options!: any[];
  @Input() title!: string;
  @Output() selectionOutput = new EventEmitter<any[]>();
  selections: any[] = [];

  @ViewChild('panel') panel!: MatExpansionPanel;
  panelStatus = false;

  constructor(public dialog: MatDialog) {}

  ngOnInit(): void {}

  launchDialog(): void {
    const dialogRef = this.dialog.open(DialogComponent, {
      maxWidth: '1000px',
      maxHeight: '90%',
      width: '90%',
      data: {
        title: this.title,
        options: this.options
          .sort((a: any, b: any) => {
            const x = a.name.toUpperCase(),
              y = b.name.toUpperCase();
            return x === y ? 0 : x > y ? 1 : -1;
          })
          .sort((a: any, b: any) => {
            return a.typeId === b.typeId ? 0 : a.typeId > b.typeId ? 1 : -1;
          }),
        selections: this.selections,
      },
    });
    dialogRef.afterClosed().subscribe((selections: any[]) => {
      selections !== undefined && selections.length !== 0 ? (this.selections = selections) : this.close();
      this.selectionOutput.emit(this.selections);
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

  private close(): void {
    this.panel.close();
    this.panelStatus = false;
  }

  private open(): void {
    this.panel.open();
    this.panelStatus = true;
  }
}
