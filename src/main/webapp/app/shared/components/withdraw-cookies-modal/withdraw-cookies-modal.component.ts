import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'jhi-withdraw-cookies-modal',
  templateUrl: './withdraw-cookies-modal.component.html',
  styleUrls: ['./withdraw-cookies-modal.component.scss'],
})
export class WithdrawCookiesModalComponent implements OnInit {
  constructor(@Inject(MAT_DIALOG_DATA) public data: number) {}

  ngOnInit(): void {
    console.warn(this.data);
  }
}
