import { Component, OnInit } from '@angular/core';
import { Account } from 'app/core/user/account.model';
import { Transaction } from 'app/shared/model/transaction.model';
import { AccountService } from 'app/core/auth/account.service';
import { TransactionService } from 'app/entities/transaction/transaction.service';
import { HttpResponse } from '@angular/common/http';

import pdfMake from 'pdfmake/build/pdfmake';
import pdfFonts from 'pdfmake/build/vfs_fonts';
import { DatePipe } from '@angular/common';

pdfMake.vfs = pdfFonts.pdfMake.vfs;

class Product {
  name = 'cookie';
  price = 0;
  qty = 0;

  constructor(name: string, price: number, qty: number) {
    this.name = name;
    this.price = price;
    this.qty = qty;
  }
}

class Invoice {
  customerName = 'no name';
  address = 'no address';
  contactNo = 0;
  email = 'noEmail';

  products: Product[] = [];
  additionalDetails = 'no aditional details';

  constructor() {}
}

@Component({
  selector: 'jhi-view-transactions',
  templateUrl: './view-transactions.component.html',
  styleUrls: ['./view-transactions.component.scss'],
})
export class ViewTransactionsComponent implements OnInit {
  invoice = new Invoice();
  account?: Account;
  transactions: Transaction[] = [];
  displayedColumns: string[] = ['cost', 'description', 'cookies', 'date', 'id'];

  constructor(private accountService: AccountService, private transactionService: TransactionService, private datePipe: DatePipe) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(res => this.onSuccess(res || undefined));
  }

  print(transaction: Transaction): void {
    this.invoice.products.push(new Product('cookie bundle x' + transaction.cookiesAmount, transaction.amount || 0, 1));
    const docDefinition = {
      content: [
        {
          text: 'Shopped',
          fontSize: 16,
          alignment: 'center',
          color: 'black',
        },
        {
          text: 'INVOICE',
          fontSize: 18,
          bold: true,
          alignment: 'center',
          decoration: 'underline',
          color: '#ff9a83',
        },
        {
          text: 'Customer Details',
          style: 'sectionHeader',
        },
        {
          columns: [
            [
              {
                text: this.account?.firstName,
                bold: true,
              },
              { text: this.account?.email },
              { text: this.account?.login },
            ],
            [
              {
                text: this.datePipe.transform(transaction.created, 'yyyy-MM-dd'),
                alignment: 'right',
              },
              {
                text: `Bill No : ${transaction.id}`,
                alignment: 'right',
              },
            ],
          ],
        },
        {
          text: 'Order Details',
          style: 'sectionHeader',
        },
        {
          table: {
            headerRows: 1,
            widths: ['*', 'auto', 'auto', 'auto'],
            body: [
              ['Product', 'Price', 'Quantity', 'Amount'],
              ...this.invoice.products.map(p => [p.name, p.price, p.qty, ((p.price || 0) * (p.qty || 0)).toFixed(2)]),
              [
                { text: 'Total Amount', colSpan: 3 },
                {},
                {},
                this.invoice.products.reduce((sum, p) => sum + (p.price || 0) * (p.qty || 0), 0).toFixed(2),
              ],
            ],
          },
        },
        {
          text: 'Additional Details',
          style: 'sectionHeader',
        },
        {
          text: transaction.description,
          margin: [0, 0, 0, 15],
        },
        {
          columns: [[{ qr: `${this.invoice.customerName}`, fit: '50' }], [{ text: 'Signature', alignment: 'right', italics: true }]],
        },
        {
          text: 'Terms and Conditions',
          style: 'sectionHeader',
        },
        {
          ul: ['Cookies are not refundable'],
        },
      ],
      styles: {
        sectionHeader: {
          bold: true,
          decoration: 'underline',
          fontSize: 14,
          margin: [0, 15, 0, 15],
        },
      },
    };

    // eslint-disable-next-line
    // @ts-ignore
    pdfMake.createPdf(docDefinition).download();

    this.invoice.products = [];
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
    }
  }
}
