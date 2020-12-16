import { Component, Input, OnInit } from '@angular/core';
import { IHash } from 'app/account/profile/dashboard/analitica/analitica.component';
import { HttpResponse } from '@angular/common/http';
import { Log } from 'app/shared/model/log.model';
import { LogService } from 'app/entities/log/log.service';
import { Account } from 'app/core/user/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'jhi-tags-posts-chart',
  templateUrl: './tags-posts-chart.component.html',
  styleUrls: ['./tags-posts-chart.component.scss'],
})
export class TagsPostsChartComponent implements OnInit {
  @Input() dated!: Date;
  @Input() maxBars = 5;
  tagPostData = [{}];
  tagPostLabel = ['Tag'];
  tagPost: IHash = {};
  account: Account | null = null;
  authSubscription?: Subscription;

  constructor(private logService: LogService, private accountService: AccountService) {}

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
    this.getTagOnPostData();
  }

  private getTagOnPostData(): void {
    this.logService
      .query({
        ...(this.dated.toISOString() && { 'created.greaterThan': this.dated.toISOString() }),
        ...(2 && { 'typeId.equals': 2 }),
      })
      .subscribe(
        (res: HttpResponse<Log[]>) => this.processTagOnPost(res.body || []),
        () => console.warn('Post fetch failed')
      );
  }

  private processTagOnPost(logs: Log[]): void {
    logs.forEach((log: Log) => {
      log.description = JSON.parse(log.description!);
      console.warn(log.description);
      if (log.description!['tag'] && log.description!['ownerId'] === this.account?.id) {
        log.description!['tag'] in this.tagPost
          ? (this.tagPost[log.description!['tag']] += 1)
          : (this.tagPost[log.description!['tag']] = 1);
      }
    });
    this.assembleTagPostData();
  }

  private sort(keys: string[]): string[] {
    const temp: string[] = [];
    keys.forEach(() => {
      let m = 0;
      for (let _i = 1; _i < keys.length; _i++) {
        if ((this.tagPost[keys[m]] < this.tagPost[keys[_i]] && !temp.includes(keys[_i])) || temp.includes(keys[m])) {
          m = _i;
        }
      }
      temp.push(keys[m]);
    });
    return temp;
  }

  private assembleTagPostData(): void {
    this.tagPostData = [];
    const keys = this.sort(Object.keys(this.tagPost));
    this.maxBars = keys.length < this.maxBars ? keys.length : this.maxBars;
    for (let i = 0; i < this.maxBars; i++) {
      const key = keys[i];
      const temp = [];
      temp.push(this.tagPost[key]);
      this.tagPostData.push({ data: temp, label: key });
    }
  }
}
