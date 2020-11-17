import { Component, Inject, OnInit } from '@angular/core';
import { Award, IAward } from 'app/shared/model/award.model';
import { FormBuilder } from '@angular/forms';
import { CookieService } from 'ngx-cookie-service';
import { AccountService } from 'app/core/auth/account.service';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { HttpResponse } from '@angular/common/http';
import { AwardService } from 'app/entities/award/award.service';

@Component({
  selector: 'jhi-award-picker-dialog',
  templateUrl: './award-picker-dialog.component.html',
  styleUrls: ['./award-picker-dialog.component.scss'],
})
export class AwardPickerDialogComponent implements OnInit {
  awardId = 0;
  award = new Award();
  account?: Account;
  awards: Award[] = [];

  constructor(
    protected service: AwardService,
    @Inject(MAT_DIALOG_DATA) public data: number,
    private _formBuilder: FormBuilder,
    private accountService: AccountService,
    private cookieService: CookieService
  ) {}

  ngOnInit(): void {
    this.service.query().subscribe(
      (res: HttpResponse<IAward[]>) => this.onSuccess(res.body),
      () => this.onError()
    );
  }

  private onError(): void {
    console.warn('There are no Awards');
  }

  private onSuccess(body: Award[] | null): void {
    this.awards = body || [];
  }
}
