import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Cookies, ICookies } from 'app/shared/model/cookies.model';
import { HttpResponse } from '@angular/common/http';
import { ICatalogue } from 'app/shared/model/catalogue.model';
import { CatalogueService } from 'app/entities/catalogue/catalogue.service';
import { Transaction } from 'app/shared/model/transaction.model';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TransactionLogicService } from 'app/shared/services/transaction-logic.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { CookiesService } from 'app/entities/cookies/cookies.service';

@Component({
  selector: 'jhi-withdraw-cookies-modal',
  templateUrl: './withdraw-cookies-modal.component.html',
  styleUrls: ['./withdraw-cookies-modal.component.scss'],
})
export class WithdrawCookiesModalComponent implements OnInit {
  cookieAmount = 0;
  catalogue = 20;
  transac: any;
  account: Account | undefined;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: Cookies,
    private catalogueService: CatalogueService,
    public dialogRef: MatDialogRef<WithdrawCookiesModalComponent>,
    public transactionLogicService: TransactionLogicService,
    public cookieService: CookiesService,
    public accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.catalogueService.find(2).subscribe((res: HttpResponse<ICatalogue>) => this.setCatalogue(res.body || undefined));
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
      this.data = data[0];
    }
  }

  public setCatalogue(catalogue: ICatalogue | undefined): void {
    if (catalogue !== undefined) {
      this.catalogue = +(catalogue.value || 20);
    }
  }

  open(): void {
    // TODO simulate withdrawal

    this.makeTransaction();
    this.dialogRef.close();
  }

  makeTransaction(): void {
    const transaction = () => {
      this.transac = {
        ...new Transaction(),
        id: undefined,
        amount: this.cookieAmount / this.catalogue,
        created: new Date() ? moment(new Date(), DATE_TIME_FORMAT) : undefined,
        description: 'Withdrew cookies in shopped',
        cookiesAmount: this.cookieAmount,
        userId: this.data.userId,
      };
    };
    transaction();

    console.warn(this.transac);

    this.transactionLogicService.processTransactionWithdraw(this.transac);
  }
}
