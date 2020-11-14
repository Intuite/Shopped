import { Injectable } from '@angular/core';
import { ITransaction } from 'app/shared/model/transaction.model';
import { CookiesService } from 'app/entities/cookies/cookies.service';
import { TransactionService } from 'app/entities/transaction/transaction.service';

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

    let user = transac.userId;
  }
}
