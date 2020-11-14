import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { TransactionService } from 'app/entities/transaction/transaction.service';
import { ITransaction, Transaction } from 'app/shared/model/transaction.model';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

declare let paypal: any;

@Component({
  selector: 'jhi-check-out',
  templateUrl: './check-out.component.html',
  styleUrls: ['./check-out.component.scss'],
})
export class CheckOutComponent implements OnInit {
  @ViewChild('paypal', { static: true }) paypalElement: ElementRef | undefined;
  @Input() price: number | undefined;
  @Input() cookies: number | undefined;
  @Input() userId: number | undefined;
  transac: any;

  product = {
    price: 3,
    description: 'Shopped, Cookies',
  };

  constructor(private transactionService: TransactionService) {}

  ngOnInit(): void {
    const cookie = this.cookies;
    const transactionService = this.transactionService;
    const userId = this.userId;
    /* eslint-disable @typescript-eslint/camelcase */
    this.product.price = this.price || 0;

    function saveThings(transac: ITransaction): void {
      console.warn(transac);
      transac.userId = userId;
      transac.cookiesAmount = cookie;
      transactionService.create(transac).subscribe(
        () => console.warn('success'),
        () => console.warn('error')
      );
    }

    paypal
      .Buttons({
        createOrder: (data: any, actions: any) => {
          return actions.order.create({
            purchase_units: [
              {
                description: this.product.description,
                amount: {
                  currency_code: 'USD',
                  value: this.product.price,
                },
              },
            ],
          });
        },
        async onApprove(data: any, actions: any): Promise<any> {
          const order = await actions.order.capture();
          console.warn(order);

          const transaction = (ord: any) => {
            this.transac = {
              ...new Transaction(),
              id: undefined,
              amount: ord.purchase_units[0].amount.value,
              created: new Date() ? moment(new Date(), DATE_TIME_FORMAT) : undefined,
              description: 'Bought cookies in shopped',
              cookiesAmount: this.cookies,
              userId: this.userId,
            };
          };
          transaction(order);
          saveThings(this.transac);
          console.warn(this.transac);
        },
        onError(err: any): any {
          console.warn(err);
        },
      })
      .render(this.paypalElement!.nativeElement);
  }
}
