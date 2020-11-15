import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { ITransaction, Transaction } from 'app/shared/model/transaction.model';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TransactionLogicService } from 'app/shared/services/transaction-logic.service';
import { MatDialogRef } from '@angular/material/dialog';
import { BundlePickerDialogComponent } from 'app/shared/components/buy-cookie/bundle-picker-dialog/bundle-picker-dialog.component';

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

  constructor(private transactionLogicService: TransactionLogicService, public dialogRef: MatDialogRef<BundlePickerDialogComponent>) {}

  ngOnInit(): void {
    const dialog = this.dialogRef;
    const cookie = this.cookies;
    const transactionLogicService = this.transactionLogicService;
    const userId = this.userId;
    /* eslint-disable @typescript-eslint/camelcase */
    this.product.price = this.price || 0;

    function processTransaction(transac: ITransaction): void {
      transac.userId = userId;
      transac.cookiesAmount = cookie;

      transactionLogicService.processTransactionBuy(transac);
      dialog.close();
      // TODO llamar transaction-logic-service con transac ya armado.
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
          processTransaction(this.transac);
          console.warn(this.transac);
        },
        onError(err: any): any {
          console.warn(err);
        },
      })
      .render(this.paypalElement!.nativeElement);
  }
}
