import { Injectable } from '@angular/core';
import { ITransaction } from 'app/shared/model/transaction.model';
import { CookiesService } from 'app/entities/cookies/cookies.service';
import { TransactionService } from 'app/entities/transaction/transaction.service';
import { HttpResponse } from '@angular/common/http';
import { Cookies, ICookies } from 'app/shared/model/cookies.model';

@Injectable({
  providedIn: 'root',
})
export class TransactionLogicService {
  constructor(private transactionService: TransactionService, private cookiesService: CookiesService) {}

  processTransactionBuy(transac: ITransaction): void {
    console.warn(transac);
    this.transactionService.create(transac).subscribe(
      res => console.warn(res),
      () => console.warn('error')
    );

    const user = transac.userId;
    console.warn(user);

    let cookie: ICookies[] | null;

    this.cookiesService
      .query({
        ...(user && { 'userId.equals': user }),
      })
      .subscribe(
        (res: HttpResponse<ICookies[]>) => this.addCookies(res.body, transac.amount),
        () => console.warn('no wallet found')
      );

    this.generateInvoice(transac);
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

  private generateInvoice(transac: ITransaction): void {}
}
