import { Component, OnDestroy, OnInit } from '@angular/core';
import { CookiesService } from 'app/entities/cookies/cookies.service';
import { Cookies, ICookies } from 'app/shared/model/cookies.model';
import { HttpResponse } from '@angular/common/http';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';

@Component({
  selector: 'jhi-dashboard-payments',
  templateUrl: './payments.component.html',
  styleUrls: ['./payments.component.scss'],
})
export class PaymentsComponent implements OnInit, OnDestroy {
  account: Account | undefined;
  cookies!: Cookies | undefined;
  private id: NodeJS.Timeout | undefined;

  constructor(private cookiesService: CookiesService, private accountService: AccountService) {}

  ngOnInit(): void {
    this.load();
    this.id = setInterval(() => {
      this.load();
    }, 4000);
  }

  ngOnDestroy(): void {
    if (this.id) {
      clearInterval(this.id);
    }
  }

  private load(): void {
    this.accountService.identity().subscribe(res => this.onSuccess(res || undefined));
  }
  private onSuccess(account: Account | undefined): void {
    if (account) {
      this.account = account;
      this.cookiesService
        .query({
          ...(account?.id && { 'userId.equals': account?.id }),
        })
        .subscribe((res: HttpResponse<ICookies[]>) => {
          if (res.body) {
            this.cookies = res.body[0];
          } else this.cookies = undefined;
        });
    }
  }
}
