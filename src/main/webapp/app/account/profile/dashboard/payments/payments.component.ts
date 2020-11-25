import { Component, Input, OnInit } from '@angular/core';
import { CookiesService } from 'app/entities/cookies/cookies.service';
import { Cookies, ICookies } from 'app/shared/model/cookies.model';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-dashboard-payments',
  templateUrl: './payments.component.html',
  styleUrls: ['./payments.component.scss'],
})
export class PaymentsComponent implements OnInit {
  @Input() account!: Account;
  cookies!: Cookies | undefined;

  constructor(private cookiesService: CookiesService) {}

  ngOnInit(): void {
    this.cookiesService
      .query({
        ...(this.account?.id && { 'userId.equals': this.account?.id }),
      })
      .subscribe((res: HttpResponse<ICookies[]>) => {
        if (res.body) {
          this.cookies = res.body[0];
        } else this.cookies = undefined;
      });
  }
}
