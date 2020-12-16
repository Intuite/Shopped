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
  rhiData = [{}];
  rhiLabel = ['Ingredient'];
  rhi: IHash = {};
  account: Account | null = null;
  authSubscription?: Subscription;

  constructor(private logService: LogService, private accountService: AccountService) {}

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
    this.getRecipeHasIngredientData();
  }

  private getRecipeHasIngredientData(): void {
    this.logService
      .query({
        ...(this.dated.toISOString() && { 'created.greaterThan': this.dated.toISOString() }),
        ...(2 && { 'typeId.equals': 2 }),
      })
      .subscribe(
        (res: HttpResponse<Log[]>) => this.processRecipeHasIngredient(res.body || []),
        () => console.warn('Post fetch failed')
      );
  }

  private processRecipeHasIngredient(logs: Log[]): void {
    let i = 0;
    let desc: any;
    while (i < logs.length) {
      desc = JSON.parse(logs[i].description!);
      if (desc!['ownerId'] !== this.account?.id) {
        logs.splice(i, 1);
      } else {
        i++;
      }
    }
    this.processDescription(logs);
  }

  private processDescription(logs: Log[]): void {
    logs.forEach((log: Log) => {
      log.description = JSON.parse(log.description!);
      console.warn(log.description);
      if (log.description!['tag'])
        log.description!['tag'] in this.rhi ? (this.rhi[log.description!['tag']] += 1) : (this.rhi[log.description!['tag']] = 1);
    });
    this.assembleRhiData();
  }

  private sort(keys: string[]): string[] {
    const temp: string[] = [];
    keys.forEach(() => {
      let m = 0;
      for (let _i = 1; _i < keys.length; _i++) {
        if ((this.rhi[keys[m]] < this.rhi[keys[_i]] && !temp.includes(keys[_i])) || temp.includes(keys[m])) {
          m = _i;
        }
      }
      temp.push(keys[m]);
    });
    return temp;
  }

  private assembleRhiData(): void {
    this.rhiData = [];
    const keys = this.sort(Object.keys(this.rhi));
    this.maxBars = keys.length < this.maxBars ? keys.length : this.maxBars;
    for (let i = 0; i < this.maxBars; i++) {
      const key = keys[i];
      const temp = [];
      temp.push(this.rhi[key]);
      this.rhiData.push({ data: temp, label: key });
    }
  }
}
