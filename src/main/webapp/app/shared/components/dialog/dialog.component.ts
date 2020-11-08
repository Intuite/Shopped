import { Component, OnInit } from '@angular/core';

import { MatDialog, MatDialogConfig } from '@angular/material/dialog';

export interface DialogData {
  name: string;
}

@Component({
  selector: 'jhi-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.scss'],
})
export class DialogComponent implements OnInit {
  name: String = '';

  constructor(public dialog: MatDialog) {}

  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  openDialog(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = '60%';

    const dialogRef = this.dialog.open(DialogComponent, {
      width: '250px',
      data: { name: this.name },
    });

    dialogRef.afterClosed().subscribe(result => {
      this.name = result;
    });
  }
}
