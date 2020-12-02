import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Bundle, IBundle } from 'app/shared/model/bundle.model';
import { BundlePickerDialogComponent } from 'app/shared/components/buy-cookie/bundle-picker-dialog/bundle-picker-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { Cookies, ICookies } from 'app/shared/model/cookies.model';
import { AccountService } from 'app/core/auth/account.service';
import { CookiesService } from 'app/entities/cookies/cookies.service';
import { Account } from 'app/core/user/account.model';
import { WithdrawCookiesModalComponent } from 'app/shared/components/withdraw-cookies-modal/withdraw-cookies-modal.component';

@Component({
  selector: 'jhi-withdraw-cookies',
  templateUrl: './withdraw-cookies.component.html',
  styleUrls: ['./withdraw-cookies.component.scss'],
})
export class WithdrawCookiesComponent implements OnInit {
  account: Account | undefined;
  cookie: Cookies | undefined;

  constructor(public dialog: MatDialog, private accountService: AccountService, private cookieService: CookiesService) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(res => (this.account = res || undefined));
    this.cookieService
      .query({
        ...(this.account?.id && { 'userId.equals': this.account?.id }),
      })
      .subscribe(
        (res: HttpResponse<ICookies[]>) => this.onSuccess(res.body || undefined),
        () => console.warn('No wallet found for user: ' + this.account?.login)
      );
  }

  protected onSuccess(data: ICookies[] | undefined): void {
    if (data !== undefined) {
      this.cookie = data[0];
    }
  }

  protected onError(): void {
    console.warn('There was an error');
  }

  open(): any {
    this.dialog.open(WithdrawCookiesModalComponent, {
      data: this.cookie,
    });
  }
}
