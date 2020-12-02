import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Cookies } from 'app/shared/model/cookies.model';

@Component({
  selector: 'jhi-withdraw-cookies-modal',
  templateUrl: './withdraw-cookies-modal.component.html',
  styleUrls: ['./withdraw-cookies-modal.component.scss'],
})
export class WithdrawCookiesModalComponent implements OnInit {
  cookieAmount = 0;

  constructor(@Inject(MAT_DIALOG_DATA) public data: Cookies) {}

  ngOnInit(): void {}
}
