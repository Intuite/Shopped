import { Component, Input, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Transaction } from 'app/shared/model/transaction.model';
import { TransactionService } from 'app/entities/transaction/transaction.service';
import { CommendationService } from 'app/entities/commendation/commendation.service';
import { LogService } from 'app/entities/log/log.service';
import { Log } from 'app/shared/model/log.model';
import { Award } from 'app/shared/model/award.model';
import { AwardService } from 'app/entities/award/award.service';

@Component({
  selector: 'jhi-analitica-p',
  templateUrl: './analitica-p.component.html',
  styleUrls: ['./analitica-p.component.scss'],
})
export class AnaliticaPComponent implements OnInit {
  data: number[];
  labels: string[];
  hashCookie: IHash = {};
  dated = new Date('Nov 15, 1900');
  barData = [{ data: [10, 15], label: 'Section A' }];
  barLabel = ['Mon', 'Tuesday'];
  lineData = [{ data: [10, 15], label: 'Section A' }];
  lineLabel = ['Mon', 'Tuesday'];
  awardData = [{ data: [10, 15], label: 'Section A' }];
  awardLabel = ['Mon', 'Tuesday'];

  names = {};
  awards: IHash = {};
  keys: string[] = [];
  hashLineIncome: IHash = {};
  hashLineExpense: IHash = {};
  hashAward: IHash = {};
  @Input() id = 0;
  amount = 0;
  private temporalOption = 3;
  private asyncLoaded = false;
  pieEmpty = false;
  line1Empty = false;
  line2Empty = false;
  topEmpty = false;

  constructor(
    private transactionService: TransactionService,
    private commendationService: CommendationService,
    private logService: LogService,
    private awardService: AwardService
  ) {
    this.data = [];
    this.labels = [];
  }

  ngOnInit(): void {
    this.initChartConfig(3);
    this.getData();
  }

  getData(): void {
    this.hashCookie['withdrawal'] = 0;
    this.hashCookie['purchase'] = 0;
    this.hashCookie['expense'] = 0;
    this.hashCookie['income'] = 0;

    this.transactionService
      .query({
        ...(this.dated.toISOString() && { 'created.greaterThan': this.dated.toISOString() }),
        ...(this.id && { 'userId.equals': this.id }),
      })
      .subscribe(
        (res: HttpResponse<Transaction[]>) => this.assembleData(res.body || []),
        () => console.warn('Transaction fetch failed')
      );

    this.logService
      .query({
        ...(this.dated.toISOString() && { 'created.greaterThan': this.dated.toISOString() }),
        ...(1 && { 'typeId.equals': 1 }),
      })
      .subscribe(
        (res: HttpResponse<Log[]>) => this.processAward(res.body || []),
        () => console.warn('Post fetch failed')
      );
  }

  public makeWeekly(): void {
    const today = new Date();
    const week = new Date(today.setDate(today.getDate() - 7));
    this.dated = week;
    this.initChartConfig(2);
    this.getData();
  }

  public makeMonthly(): void {
    const today = new Date();
    const month = new Date(today.setDate(today.getDate() - 30));
    this.dated = month;
    this.initChartConfig(3);
    this.getData();
  }

  public makeDaily(): void {
    const today = new Date();
    const daily = new Date(today.setDate(today.getDate() - 1));
    this.dated = daily;
    this.initChartConfig(1);
    this.getData();
  }

  // processes the amount spend on awards by the user
  private processAward(response: Log[]): void {
    let tempVal = 0;
    response.forEach((log: Log) => {
      if (log.description && log.created) {
        const amount = JSON.parse(log.description);
        if (log.userId === this.id) {
          this.hashCookie['expense'] += amount.awardCost;
          this.appendNewEntry(amount.awardCost, log.created, false);
        } else if (amount.recipientId === this.id) {
          tempVal = amount.awardCost - amount.awardCost * amount.tax;
          this.hashCookie['income'] += tempVal;
          this.appendNewEntry(tempVal, log.created, true);
          this.appendAwardEntry(log.created);

          if (this.awards[amount.awardId]) {
            this.awards[amount.awardId]++;
          } else {
            this.awards[amount.awardId] = 1;
          }
        }
      }
    });
    this.generateCharts();
    this.generateTimeCharts();
    this.assembleAwardChart();
    this.assembleTopAward();
  }

  private appendAwardEntry(created: moment.Moment): void {
    this.hashAward =
      this.temporalOption === 1
        ? this.appendDailyEntry(1, created, this.hashAward)
        : this.temporalOption === 2
        ? this.appendWeeklyEntry(1, created, this.hashAward)
        : this.appendMonthlyEntry(1, created, this.hashAward);
  }

  private appendNewEntry(awardCost: number, created: moment.Moment, positive: boolean): void {
    let hash = {};
    if (positive) {
      hash = this.hashLineIncome;
    } else {
      hash = this.hashLineExpense;
    }
    hash =
      this.temporalOption === 1
        ? this.appendDailyEntry(awardCost, created, hash)
        : this.temporalOption === 2
        ? this.appendWeeklyEntry(awardCost, created, hash)
        : this.appendMonthlyEntry(awardCost, created, hash);

    if (positive) {
      this.hashLineIncome = hash;
    } else {
      this.hashLineExpense = hash;
    }
  }

  // '0:00', '4:00', '8:00', '12:00', '16:00', '20:00', '24:00'
  private appendDailyEntry(awardCost: number, created: moment.Moment, hash: IHash): IHash {
    const hour = Math.trunc(created.hour() / 4);
    switch (hour) {
      case 0:
        hash['0:00'] += awardCost;
        break;
      case 1:
        hash['4:00'] += awardCost;
        break;
      case 2:
        hash['8:00'] += awardCost;
        break;
      case 3:
        hash['12:00'] += awardCost;
        break;
      case 4:
        hash['16:00'] += awardCost;
        break;
      case 5:
        hash['20:00'] += awardCost;
        break;
      case 6:
        hash['24:00'] += awardCost;
        break;
      default:
        hash['24:00'] += awardCost;
    }
    return hash;
  }

  private appendWeeklyEntry(awardCost: number, created: moment.Moment, hash: IHash): IHash {
    const day = created.weekday();
    switch (day) {
      case 1:
        hash['Mon'] += awardCost;
        break;
      case 2:
        hash['Tue'] += awardCost;
        break;
      case 3:
        hash['Wen'] += awardCost;
        break;
      case 4:
        hash['Thu'] += awardCost;
        break;
      case 5:
        hash['Fri'] += awardCost;
        break;
      case 6:
        hash['Sat'] += awardCost;
        break;
      case 7:
        hash['Sun'] += awardCost;
        break;
    }
    return hash;
  }

  // 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'
  private appendMonthlyEntry(awardCost: number, created: moment.Moment, hash: IHash): IHash {
    const month = created.month() + 1;
    switch (month) {
      case 1:
        hash['Jan'] += awardCost;
        break;
      case 2:
        hash['Feb'] += awardCost;
        break;
      case 3:
        hash['Mar'] += awardCost;
        break;
      case 4:
        hash['Apr'] += awardCost;
        break;
      case 5:
        hash['May'] += awardCost;
        break;
      case 6:
        hash['Jun'] += awardCost;
        break;
      case 7:
        hash['Jul'] += awardCost;
        break;
      case 8:
        hash['Aug'] += awardCost;
        break;
      case 9:
        hash['Sep'] += awardCost;
        break;
      case 10:
        hash['Oct'] += awardCost;
        break;
      case 11:
        hash['Nov'] += awardCost;
        break;
      case 12:
        hash['Dec'] += awardCost;
        break;
    }
    return hash;
  }

  private assembleData(transactions: Transaction[]): void {
    transactions.forEach((transaction: Transaction) => {
      if (
        transaction.description === 'Withdrew cookies in shopped' &&
        transaction.cookiesAmount &&
        transaction.amount &&
        transaction.created
      ) {
        this.hashCookie['withdrawal'] += transaction.cookiesAmount;
        this.appendNewEntry(transaction.cookiesAmount, transaction.created, false);
      } else if (transaction.cookiesAmount && transaction.amount && transaction.created) {
        this.hashCookie['purchase'] += transaction.cookiesAmount;
        this.appendNewEntry(transaction.cookiesAmount, transaction.created, true);
      }
    });
    this.generateTimeCharts();
    this.generateCharts();
  }

  private generateCharts(): void {
    this.data = [];
    this.data.push(this.hashCookie['withdrawal']);
    this.data.push(this.hashCookie['purchase']);
    this.data.push(this.hashCookie['expense']);
    this.data.push(this.hashCookie['income']);

    this.labels = Object.keys(this.hashCookie);
    if (this.data.every(item => item === 0)) {
      this.pieEmpty = true;
    } else {
      this.pieEmpty = false;
    }
  }

  private assembleAwardChart(): void {
    this.awardData = [];
    this.awardLabel = [];

    this.awardData.push({ data: Object.values(this.hashAward), label: 'Awards' });
    this.awardLabel = Object.keys(this.hashAward);
    console.warn(this.hashAward);
    if (Object.values(this.hashAward).every(item => item === 0)) {
      this.line2Empty = true;
    } else {
      this.line2Empty = false;
    }
  }

  private generateTimeCharts(): void {
    if (this.asyncLoaded) {
      this.lineData = [];
      this.lineData.push({ data: Object.values(this.hashLineExpense), label: 'Expense' });
      this.lineData.push({ data: Object.values(this.hashLineIncome), label: 'Income' });

      this.lineLabel = Object.keys(this.hashLineIncome);
      console.warn(Object.values(this.hashLineIncome));
      console.warn(Object.values(this.hashLineExpense));
      if (Object.values(this.hashLineIncome).every(item => item === 0) && Object.values(this.hashLineExpense).every(item => item === 0)) {
        this.line1Empty = true;
      } else {
        this.line1Empty = false;
      }
    } else {
      this.asyncLoaded = true;
    }
  }

  private assembleTopAward(): void {
    this.keys = Object.keys(this.awards);
    // this.sort(this.keys);

    this.awardService
      .query({
        ...(this.dated.toISOString() && { 'created.greaterThan': this.dated.toISOString() }),
        ...(this.keys && { 'id.in': this.keys }),
      })
      .subscribe(
        (res: HttpResponse<Award[]>) => this.changeNames(res.body || []),
        () => console.warn('Award fetch failed')
      );
  }

  private changeNames(body: Award[]): void {
    body.forEach(award => {
      if (award.id) {
        if (!this.names[award.id]) {
          this.names[award.id] = award.name;
        }
      }
    });
    if (body.length === 0) {
      this.topEmpty = true;
    } else {
      this.topEmpty = false;
    }
    this.sort(this.keys);
  }

  private sort(keys: string[]): void {
    const temp: string[] = [];
    let m;
    for (let _j = 0; _j < keys.length; _j++) {
      m = 0;
      for (let _i = 1; _i < keys.length; _i++) {
        if ((this.awards[keys[m]] < this.awards[keys[_i]] && !temp.includes(keys[_i])) || temp.includes(keys[m])) {
          m = _i;
        }
      }
      temp.push(keys[m]);
    }
    this.keys = temp;
  }

  private initChartConfig(number: number): void {
    this.hashLineIncome = {};
    this.hashLineExpense = {};
    this.asyncLoaded = false;
    this.lineData = [];
    this.hashAward = {};
    this.awardData = [];
    this.awardLabel = [];
    this.names = {};
    this.awards = {};
    const daily = ['0:00', '4:00', '8:00', '12:00', '16:00', '20:00', '24:00'];
    const weekly = ['Mon', 'Tue', 'Wen', 'Thu', 'Fri', 'Sat', 'Sun'];
    const monthly = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
    this.temporalOption = number;
    const iter = (this.lineLabel = number === 1 ? daily : number === 2 ? weekly : monthly);
    iter.forEach(i => {
      this.hashLineIncome[i] = 0;
      this.hashLineExpense[i] = 0;
      this.hashAward[i] = 0;
    });
  }
}

export interface IHash {
  [details: string]: number;
}
