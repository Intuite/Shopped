import { Injectable } from '@angular/core';
import { ITransaction } from 'app/shared/model/transaction.model';
import { CookiesService } from 'app/entities/cookies/cookies.service';
import { TransactionService } from 'app/entities/transaction/transaction.service';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { ICookies } from 'app/shared/model/cookies.model';
import { SERVER_API_URL } from 'app/app.constants';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class TransactionLogicService {
  public resourceUrl = SERVER_API_URL + 'api/transactions';

  constructor(
    private transactionService: TransactionService,
    private cookiesService: CookiesService,
    protected http: HttpClient,
    protected router: Router
  ) {}

  processTransactionWithdraw(transac: ITransaction): void {
    this.transactionService.create(transac).subscribe(
      res => ((transac = res.body || transac), this.withdraw(transac)),
      () => console.warn('error')
    );
  }
  processTransactionBuy(transac: ITransaction): void {
    this.transactionService.create(transac).subscribe(
      res => ((transac = res.body || transac), this.continue(transac)),
      () => console.warn('error')
    );
  }

  addCookies(wallet: ICookies[] | null, amount: number | undefined): void {
    if (wallet !== null) {
      wallet[0].amount = (wallet[0].amount || 0) + (amount || 0);
      this.cookiesService.update(wallet[0]).subscribe(
        () => console.warn('success'),
        () => console.warn('error')
      );
    }
  }

  private continue(transac: ITransaction): void {
    const user = transac.userId;

    this.cookiesService
      .query({
        ...(user && { 'userId.equals': user }),
      })
      .subscribe(
        (res: HttpResponse<ICookies[]>) => this.addCookies(res.body, transac.cookiesAmount),
        () => console.warn('no wallet found')
      );
  }

  private withdraw(transac: ITransaction): void {
    const user = transac.userId;

    this.cookiesService
      .query({
        ...(user && { 'userId.equals': user }),
      })
      .subscribe(
        (res: HttpResponse<ICookies[]>) => this.reduceCookies(res.body, transac.cookiesAmount),
        () => console.warn('no wallet found')
      );
  }

  private reduceCookies(wallet: ICookies[] | null, amount: number | undefined): void {
    if (wallet !== null) {
      wallet[0].amount = (wallet[0].amount || 0) - (amount || 0);
      this.cookiesService.update(wallet[0]).subscribe(
        () => console.warn('success'),
        () => console.warn('error')
      );
    }
  }
}
