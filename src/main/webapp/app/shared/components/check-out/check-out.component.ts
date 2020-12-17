import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { ITransaction, Transaction } from 'app/shared/model/transaction.model';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TransactionLogicService } from 'app/shared/services/transaction-logic.service';
import { MatDialogRef } from '@angular/material/dialog';
import { BundlePickerDialogComponent } from 'app/shared/components/buy-cookie/bundle-picker-dialog/bundle-picker-dialog.component';
import { IClientAuthorizeCallbackData, ICreateOrderRequest, IOnApproveCallbackData, IPayPalConfig } from 'ngx-paypal';

// declare let paypal: any;

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
  public payPalConfig?: IPayPalConfig;
  public stringPrice = '';
  transac: any;

  product = {
    price: 3,
    description: 'Shopped, Cookies',
  };

  constructor(private transactionLogicService: TransactionLogicService, public dialogRef: MatDialogRef<BundlePickerDialogComponent>) {}

  ngOnInit(): void {
    this.initConfig();
    this.stringPrice = (this.price || 0).toString();

    /* const dialog = this.dialogRef;
    const cookie = this.cookies;
    const transactionLogicService = this.transactionLogicService;
    const userId = this.userId;
    /!* eslint-disable @typescript-eslint/camelcase *!/
    this.product.price = this.price || 0;*/

    /* function processTransaction(transac: ITransaction): void {
      transac.userId = userId;
      transac.cookiesAmount = cookie;

      transactionLogicService.processTransactionBuy(transac);
      dialog.close();
    }*/

    /* paypal
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
        },
        onError(err: any): any {
          console.warn(err);
        },
      })
      .render(this.paypalElement!.nativeElement);*/
  }

  private processTransaction(transac: ITransaction): void {
    transac.userId = this.userId;
    transac.cookiesAmount = this.cookies;

    this.transactionLogicService.processTransactionBuy(transac);
    this.dialogRef.close();
  }
  private initConfig(): void {
    /* eslint-disable @typescript-eslint/camelcase */
    /* eslint-disable @typescript-eslint/consistent-type-assertions*/
    this.payPalConfig = {
      currency: 'USD',
      clientId: 'ARocX7FC56s4Bn0yoD-0a8B-VnXbhqdh48Da-ljMHiYxiDm_9RR4K7npNwBEopCq9EqHBQq86OU-hG5R',
      createOrderOnClient: () =>
        <ICreateOrderRequest>{
          intent: 'CAPTURE',
          purchase_units: [
            {
              amount: {
                currency_code: 'USD',
                value: this.stringPrice,
                breakdown: {
                  item_total: {
                    currency_code: 'USD',
                    value: this.stringPrice,
                  },
                },
              },
              items: [
                {
                  name: 'Shopped cookies',
                  quantity: '1',
                  category: 'DIGITAL_GOODS',
                  unit_amount: {
                    currency_code: 'USD',
                    value: this.stringPrice,
                  },
                },
              ],
            },
          ],
        },
      advanced: {
        commit: 'true',
      },
      style: {
        label: 'paypal',
        layout: 'vertical',
        size: 'small',
        color: 'blue',
        shape: 'rect',
      },
      onApprove: (data, actions) => this.processAproval(data, actions),
      onClientAuthorization: data => this.processOrder(data),
      onError: err => this.processError(err),
    };
  }

  processOrder(data: IClientAuthorizeCallbackData): void {
    console.warn('your order has been authorized', data);

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
    transaction(data);
    this.processTransaction(this.transac);
  }

  processError(error: any): void {
    console.warn('An error has ocurred on your payPal transaction', error);
  }

  processAproval(data: IOnApproveCallbackData, actions: any): void {
    console.warn('onApprove - transaction was approved, but not authorized', data, actions);
    actions.order.get().then((details: any) => {
      console.warn('onApprove - you can get full order details inside onApprove: ', details);
    });
  }
}
