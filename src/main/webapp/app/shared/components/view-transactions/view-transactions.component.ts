import { Component, OnInit } from '@angular/core';
import { Account } from 'app/core/user/account.model';
import { Transaction } from 'app/shared/model/transaction.model';
import { AccountService } from 'app/core/auth/account.service';
import { TransactionService } from 'app/entities/transaction/transaction.service';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-view-transactions',
  templateUrl: './view-transactions.component.html',
  styleUrls: ['./view-transactions.component.scss'],
})
export class ViewTransactionsComponent implements OnInit {
  account?: Account;
  transactions: Transaction[] = [];

  constructor(private accountService: AccountService, private transactionService: TransactionService) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(res => this.onSuccess(res || undefined));
  }

  private onSuccess(account: Account | undefined): void {
    if (account !== undefined) {
      this.account = account;
      this.transactionService
        .query({
          size: 50,
          ...(this.account?.id && { 'userId.equals': this.account?.id }),
        })
        .subscribe(
          (res: HttpResponse<Transaction[]>) => ((this.transactions = res.body || []), console.warn(res.body)),
          () => console.warn('Transaction fetch failed')
        );
      console.warn(this.account);
    }
  }
}
