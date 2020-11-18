import { Component, Inject, OnInit } from '@angular/core';
import { Award, IAward } from 'app/shared/model/award.model';
import { FormBuilder } from '@angular/forms';
import { AccountService } from 'app/core/auth/account.service';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { HttpResponse } from '@angular/common/http';
import { AwardService } from 'app/entities/award/award.service';
import { Account } from 'app/core/user/account.model';
import { Cookies, ICookies } from 'app/shared/model/cookies.model';
import { CookiesService } from 'app/entities/cookies/cookies.service';

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
  cookie = new Cookies();

  constructor(
    protected service: AwardService,
    @Inject(MAT_DIALOG_DATA) public data: number,
    private _formBuilder: FormBuilder,
    private accountService: AccountService,
    private cookieService: CookiesService
  ) {}

  ngOnInit(): void {
    this.service.query().subscribe(
      (res: HttpResponse<IAward[]>) => this.onSuccess(res.body),
      () => this.onError()
    );
    this.accountService.identity().subscribe(res => (this.account = res || undefined));
    this.cookieService
      .query({
        ...(this.account?.id && { 'userId.equals': this.account?.id }),
      })
      .subscribe(
        (res: HttpResponse<ICookies[]>) => this.onCookieSuccess(res.body || undefined),
        () => console.warn('se cayo')
      );
  }

  onCookieSuccess(data: any): void {
    if (data !== null && data !== undefined) {
      this.cookie = data[0];
    }
  }

  private onError(): void {
    console.warn('There are no Awards');
  }

  private onSuccess(body: Award[] | null): void {
    console.warn(body);
    this.awards = body || [];
    this.award = (body || [])[0];
  }

  chargeAward(item: Award): void {
    this.award = item;
  }
}
