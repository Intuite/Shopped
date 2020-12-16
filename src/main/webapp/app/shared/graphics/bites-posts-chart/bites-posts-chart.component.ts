import { Component, Input, OnInit } from '@angular/core';
import { IHash } from 'app/account/profile/dashboard/analitica/analitica.component';
import { HttpResponse } from '@angular/common/http';
import { Log } from 'app/shared/model/log.model';
import { LogService } from 'app/entities/log/log.service';
import { Account } from 'app/core/user/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'jhi-bites-posts-chart',
  templateUrl: './bites-posts-chart.component.html',
  styleUrls: ['./bites-posts-chart.component.scss'],
})
export class BitesPostsChartComponent implements OnInit {
  @Input() dated!: Date;
  @Input() maxBars = 5;
  bitePostData = [{}];
  bitePostLabel = ['Bite'];
  bitePost: IHash = {};
  account: Account | null = null;
  authSubscription?: Subscription;

  constructor(private logService: LogService, private accountService: AccountService) {}

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
    this.getBiteInPostData();
  }

  private getBiteInPostData(): void {
    this.logService
      .query({
        ...(this.dated.toISOString() && { 'created.greaterThan': this.dated.toISOString() }),
        ...(4 && { 'typeId.equals': 4 }),
      })
      .subscribe(
        (res: HttpResponse<Log[]>) => this.processBiteInPost(res.body || []),
        () => console.warn('Post fetch failed')
      );
  }

  private processBiteInPost(logs: Log[]): void {
    logs.forEach((log: Log) => {
      log.description = JSON.parse(log.description!);
      console.warn(log.description);
      if (log.description!['recipeName'] && log.description!['ownerId'] === this.account?.id)
        log.description!['recipeName'] in this.bitePost
          ? (this.bitePost[log.description!['recipeName']] += 1)
          : (this.bitePost[log.description!['recipeName']] = 1);
    });
    this.assembleBitePostData();
  }

  private sort(keys: string[]): string[] {
    const temp: string[] = [];
    keys.forEach(() => {
      let m = 0;
      for (let _i = 1; _i < keys.length; _i++) {
        if ((this.bitePost[keys[m]] < this.bitePost[keys[_i]] && !temp.includes(keys[_i])) || temp.includes(keys[m])) {
          m = _i;
        }
      }
      temp.push(keys[m]);
    });
    return temp;
  }

  private assembleBitePostData(): void {
    this.bitePostData = [];
    const keys = this.sort(Object.keys(this.bitePost));
    this.maxBars = keys.length < this.maxBars ? keys.length : this.maxBars;
    for (let i = 0; i < this.maxBars; i++) {
      const key = keys[i];
      const temp = [];
      temp.push(this.bitePost[key]);
      this.bitePostData.push({ data: temp, label: key });
    }
  }
}
