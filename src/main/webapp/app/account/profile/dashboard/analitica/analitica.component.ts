import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Transaction } from 'app/shared/model/transaction.model';
import { TransactionService } from 'app/entities/transaction/transaction.service';
import { CommendationService } from 'app/entities/commendation/commendation.service';
import { Commendation } from 'app/shared/model/commendation.model';

@Component({
  selector: 'jhi-analitica',
  templateUrl: './analitica.component.html',
  styleUrls: ['./analitica.component.scss'],
})
export class AnaliticaComponent implements OnInit {
  data = [300, 500, 100];
  labels = ['Download', 'Store', 'Mail Sales'];
  moneyData = [100, 200];
  moneyLabels = ['Expenses', 'Bought'];
  hashCookie: IHash = {};
  hashMoney: IHash = {};
  dated = new Date('Nov 15, 1900');
  barData = [{ data: [10, 15], label: 'Section A' }];
  barLabel = ['Mon', 'Tuesday'];
  commendation: IHash = {};
  keys: string[] = [];
  cookieEmpty = false;
  moneyEmpty = false;
  awardEmpty = false;

  constructor(private transactionService: TransactionService, private commendationService: CommendationService) {}

  ngOnInit(): void {
    this.getData();
    this.getBarData();
  }

  getData(): void {
    this.hashCookie['withdrawal'] = 0;
    this.hashCookie['purchase'] = 0;
    this.hashMoney['withdrawal'] = 0;
    this.hashMoney['purchase'] = 0;

    this.transactionService
      .query({
        ...(this.dated.toISOString() && { 'created.greaterThan': this.dated.toISOString() }),
      })
      .subscribe(
        (res: HttpResponse<Transaction[]>) => this.assembleData(res.body || []),
        () => console.warn('Transaction fetch failed')
      );
  }

  public makeWeekly(): void {
    const today = new Date();
    const week = new Date(today.setDate(today.getDate() - 7));
    this.dated = week;
    this.getData();
    this.getBarData();
  }

  public makeMonthly(): void {
    const today = new Date();
    const month = new Date(today.setDate(today.getDate() - 30));
    this.dated = month;
    this.getData();
    this.getBarData();
  }

  public makeDaily(): void {
    const today = new Date();
    const daily = new Date(today.setDate(today.getDate() - 1));
    this.dated = daily;
    this.getData();
    this.getBarData();
  }

  private assembleData(transactions: Transaction[]): void {
    transactions.forEach((transaction: Transaction) => {
      if (transaction.description === 'Withdrew cookies in shopped' && transaction.cookiesAmount && transaction.amount) {
        this.hashCookie['withdrawal'] += transaction.cookiesAmount;
        this.hashMoney['withdrawal'] += transaction.amount;
      } else if (transaction.cookiesAmount && transaction.amount) {
        this.hashCookie['purchase'] += transaction.cookiesAmount;
        this.hashMoney['purchase'] += transaction.amount;
      }
    });
    this.generateCharts();
  }

  private generateCharts(): void {
    this.data = [];
    this.labels = [];

    this.moneyData = [];
    this.moneyLabels = [];

    this.data.push(this.hashCookie['withdrawal']);
    this.data.push(this.hashCookie['purchase']);

    this.labels = Object.keys(this.hashCookie);

    this.moneyData.push(this.hashMoney['withdrawal']);
    this.moneyData.push(this.hashMoney['purchase']);

    this.moneyLabels = Object.keys(this.hashMoney);

    if (this.data.every(item => item === 0)) {
      this.cookieEmpty = true;
      this.moneyEmpty = true;
    } else {
      this.cookieEmpty = false;
      this.moneyEmpty = false;
    }
  }

  private getBarData(): void {
    this.commendation = {};
    this.commendationService
      .query({
        ...(this.dated.toISOString() && { 'date.greaterThan': this.dated.toISOString() }),
      })
      .subscribe(
        (res: HttpResponse<Commendation[]>) => this.processCommendation(res.body || []),
        () => console.warn('Commendation fetch failed')
      );
  }

  private processCommendation(commendations: Commendation[]): void {
    commendations.forEach((commendation: Commendation) => {
      if (commendation.awardName) {
        if (commendation.awardName in this.commendation) {
          this.commendation[commendation.awardName] += 1;
        } else {
          this.commendation[commendation.awardName] = 1;
        }
      }
    });
    this.assembleBarData();
  }

  private assembleBarData(): void {
    this.barData = [];
    this.barLabel = ['total'];

    this.keys = Object.keys(this.commendation);
    this.sort(this.keys);

    this.keys.forEach((key: string) => {
      const temp = [];
      temp.push(this.commendation[key]);
      this.barData.push({ data: temp, label: key });
    });

    if (Object.values(this.commendation).every(item => item === 0)) {
      this.awardEmpty = true;
    } else {
      this.awardEmpty = false;
    }
  }

  private sort(keys: string[]): void {
    const temp: string[] = [];
    let m;
    for (let _j = 0; _j < keys.length; _j++) {
      m = 0;
      for (let _i = 1; _i < keys.length; _i++) {
        // console.warn('_m: ' + keys[m] + '  ' + m);
        // console.warn('_i: ' + keys[_i] + '  ' + _i);
        // console.warn(temp);
        // console.warn('commendations: ' + this.commendation[m] + ' vs ' + this.commendation[_i]);
        if ((this.commendation[keys[m]] < this.commendation[keys[_i]] && !temp.includes(keys[_i])) || temp.includes(keys[m])) {
          // console.warn('found that ' + keys[m] + ' is smaller than ' + keys[_i]);
          m = _i;
        }
      }
      // console.warn('adding ' + keys[m]);
      temp.push(keys[m]);
    }
    this.keys = temp;
  }
}

export interface IHash {
  [details: string]: number;
}
