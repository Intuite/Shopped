import { Component, Input, OnInit } from '@angular/core';
import { IHash } from 'app/account/profile/dashboard/analitica/analitica.component';
import { HttpResponse } from '@angular/common/http';
import { Log } from 'app/shared/model/log.model';
import { LogService } from 'app/entities/log/log.service';

@Component({
  selector: 'jhi-ingredient-recipes-chart',
  templateUrl: './ingredient-recipes-chart.component.html',
  styleUrls: ['./ingredient-recipes-chart.component.scss'],
})
export class IngredientRecipesChartComponent implements OnInit {
  @Input() dated!: Date;
  @Input() maxBars = 5;
  rhiData = [{}];
  rhiLabel = ['Ingredient'];
  rhi: IHash = {};

  constructor(private logService: LogService) {}

  ngOnInit(): void {
    this.getRecipeHasIngredientData();
  }

  private getRecipeHasIngredientData(): void {
    this.logService
      .query({
        ...(this.dated.toISOString() && { 'created.greaterThan': this.dated.toISOString() }),
        ...(7 && { 'typeId.equals': 7 }),
      })
      .subscribe(
        (res: HttpResponse<Log[]>) => this.processRecipeHasIngredient(res.body || []),
        () => console.warn('Post fetch failed')
      );
  }

  private processRecipeHasIngredient(logs: Log[]): void {
    logs.forEach((log: Log) => {
      log.description = JSON.parse(log.description!);
      console.warn(log.description);
      if (log.description!['ingredientName'])
        log.description!['ingredientName'] in this.rhi
          ? (this.rhi[log.description!['ingredientName']] += 1)
          : (this.rhi[log.description!['ingredientName']] = 1);
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
