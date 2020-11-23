import { AfterViewInit, Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IRecipe } from 'app/shared/model/recipe.model';
import { RecipeService } from 'app/entities/recipe/recipe.service';
import { HttpResponse } from '@angular/common/http';
import { Account } from 'app/core/user/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'jhi-recipe-user-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.scss'],
})
export class RecipeListComponent implements OnInit, AfterViewInit {
  // @Input() recipes!: IRecipe[];

  recipes: IRecipe[] = [];
  account?: Account;
  user!: IUser;
  statusOptions = ['ACTIVE', 'INACTIVE'];
  dataSource = new MatTableDataSource<IRecipe>();

  constructor(
    protected dataUtils: JhiDataUtils,
    protected activatedRoute: ActivatedRoute,
    protected recipeService: RecipeService,
    protected userService: UserService,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(account => {
      if (account) {
        this.userService.find(account.login).subscribe(user => (this.user = user));
      }
    });

    this.recipeService.query().subscribe(
      (res: HttpResponse<IRecipe[]>) => this.onSuccess(res.body),
      () => this.onError()
    );

    this.dataSource.data = this.recipes;
  }

  ngAfterViewInit(): void {
    // this.dataSource.sort = this.sort;
    this.dataSource.filterPredicate = (data: any, filter) => {
      let dataStr = JSON.stringify(data).toLowerCase();
      dataStr = dataStr.replace(/(\{|,)\s*(.+?)\s*:/g, '');
      return dataStr.includes(filter);
    };
  }

  cleanRecipes(): void {
    let i = 0;
    while (i < this.recipes.length) {
      if (this.recipes[i].userId !== this.user.id || this.recipes[i].status === this.statusOptions[1]) {
        this.recipes.splice(i, 1);
      } else {
        i++;
      }
    }
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }

  private onError(): void {
    console.warn('There are no Recipes');
  }

  private onSuccess(body: IRecipe[] | null): void {
    this.recipes = body || [];
    this.cleanRecipes();
  }

  public filter = (e: Event) => {
    this.dataSource.filter = (e.target as HTMLInputElement).value.trim().toLocaleLowerCase();
  };

  public reloadSource(data: IRecipe[]): void {
    this.recipes = data;
    this.dataSource.data = data;
  }
}
