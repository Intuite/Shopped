import { Component, Input, OnInit } from '@angular/core';
import { Award } from 'app/shared/model/award.model';
import { MatDialog } from '@angular/material/dialog';
import { AwardPickerDialogComponent } from 'app/shared/components/giveAward/award-picker-dialog/award-picker-dialog.component';

@Component({
  selector: 'jhi-give-award',
  templateUrl: './give-award.component.html',
  styleUrls: ['./give-award.component.scss'],
})
export class GiveAwardComponent implements OnInit {
  awards?: Award[];
  @Input() data: number | undefined;
  constructor(public dialog: MatDialog) {}

  ngOnInit(): void {}

  open(): any {
    if (this.data === undefined) {
      this.data = 0;
    }
    this.dialog.open(AwardPickerDialogComponent, { data: this.data });
  }
}
