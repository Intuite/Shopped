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
import { PostService } from 'app/entities/post/post.service';
import { IPost, Post } from 'app/shared/model/post.model';
import { CatalogueService } from 'app/entities/catalogue/catalogue.service';
import { Catalogue, ICatalogue } from 'app/shared/model/catalogue.model';
import { LogService } from 'app/entities/log/log.service';
import { CommendationService } from 'app/entities/commendation/commendation.service';
import { Commendation } from 'app/shared/model/commendation.model';
import moment from 'moment';
import { Log } from 'app/shared/model/log.model';

@Component({
  selector: 'jhi-award-picker-dialog',
  templateUrl: './award-picker-dialog.component.html',
  styleUrls: ['./award-picker-dialog.component.scss'],
})
export class AwardPickerDialogComponent implements OnInit {
  award = new Award();
  account?: Account;
  awards: Award[] = [];
  cookie = new Cookies();
  post = new Post();
  catalogue = new Catalogue();

  constructor(
    protected service: AwardService,
    @Inject(MAT_DIALOG_DATA) public data: number,
    private _formBuilder: FormBuilder,
    private accountService: AccountService,
    private cookieService: CookiesService,
    private postService: PostService,
    private catalogueService: CatalogueService,
    private commendationService: CommendationService,
    private logService: LogService
  ) {}

  ngOnInit(): void {
    this.service.query().subscribe(
      (res: HttpResponse<IAward[]>) => this.onSuccess(res.body),
      () => this.onError()
    );
    this.accountService.identity().subscribe(res => this.onAccountSuccess(res || undefined));
    this.postService.find(this.data).subscribe(
      (res: HttpResponse<IPost>) => this.onSuccessPost(res.body || undefined),
      () => console.warn('no such post')
    );
    this.catalogueService.find(1).subscribe((res: HttpResponse<ICatalogue>) => this.setBundle(res.body || undefined));
  }

  onAccountSuccess(account: Account | undefined): void {
    if (account !== undefined) {
      this.account = account;
      this.cookieService
        .query({
          ...(this.account?.id && { 'userId.equals': this.account?.id }),
        })
        .subscribe(
          (res: HttpResponse<ICookies[]>) => this.onCookieSuccess(res.body || undefined),
          () => console.warn('se cayo')
        );
    }
  }

  onCookieSuccess(data: any): void {
    if (data !== null && data !== undefined) {
      this.cookie = data[0];
    }
  }

  chargeAward(item: Award): void {
    this.award = item;
  }

  awardPost(award: Award): void {
    this.cookieService
      .query({
        ...(this.post.userId && { 'userId.equals': this.post.userId }),
      })
      .subscribe(
        (res: HttpResponse<ICookies[]>) => this.onRecipientFound(res.body || [], award),
        () => console.warn('No user found for post')
      );
  }

  private onError(): void {
    console.warn('There are no Awards');
  }

  private onSuccess(body: Award[] | null): void {
    console.warn(body);
    this.awards = body || [];
    this.award = (body || [])[0];
  }

  private onSuccessPost(param: IPost | undefined): void {
    if (param !== undefined) {
      this.post = param;
    }
  }

  private onRecipientFound(data: ICookies[], award: Award): void {
    let wallet: Cookies;
    if (data !== null && data !== undefined) {
      wallet = data[0];

      // update the recipient wallet
      let tax: number = +(this.catalogue.value || 0);
      tax = tax / 100;
      const awardCost = award.cost || 0;
      wallet.amount = (wallet.amount || 0) + awardCost - awardCost * tax;

      this.cookieService.update(wallet).subscribe(
        () => console.warn('cookies added to recipient'),
        () => console.warn('error')
      );

      // substract to the emisor wallet
      this.cookie.amount = (this.cookie.amount || 0) - awardCost;

      this.cookieService.update(this.cookie).subscribe(
        () => console.warn('cookies subtracted to emisor'),
        () => console.warn('error')
      );

      // add tax to the admin wallet
      this.cookieService
        .query({
          ...(3 && { 'userId.equals': 3 }),
        })
        .subscribe(
          (res: HttpResponse<ICookies[]>) => this.giveTax(res.body || [], awardCost * tax),
          () => console.warn('No wallet found for admin')
        );
      // add comendation
      const commendation = new Commendation(undefined, moment(), this.data, award.name, award.id, this.account?.login, this.account?.id);
      this.commendationService.create(commendation).subscribe(
        () => console.warn('commendation succesful'),
        () => console.warn('error in commendation')
      );

      // add history
      const description = JSON.stringify({
        recipientId: this.post.userId,
        emissorId: this.account?.id,
        awardId: this.award.id,
        awardCost: this.award.cost,
        tax: +tax,
      });
      console.warn(description);
      this.logService.create(new Log(undefined, description, moment(), 'Award', 1, this.account?.login, this.account?.id)).subscribe(
        () => console.warn('log succesful'),
        () => console.warn('log failed')
      );
    }
  }

  private setBundle(param: ICatalogue | undefined): void {
    if (param !== undefined) {
      this.catalogue = param;
    }
  }

  private giveTax(data: ICookies[], tax: number): void {
    let cookie: Cookies;
    if (data !== undefined) {
      cookie = data[0];
      cookie.amount = (cookie.amount || 0) + tax;
      this.cookieService.update(cookie).subscribe(
        () => console.warn('taxes subtracted'),
        () => console.warn('error on tax')
      );
    }
  }
}
