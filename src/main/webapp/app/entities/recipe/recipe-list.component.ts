import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { Recipe, IRecipe } from 'app/shared/model/recipe.model';
import { RecipeService } from 'app/entities/recipe/recipe.service';
import { HttpResponse } from '@angular/common/http';
import { Account } from 'app/core/user/account.model';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-recipe-user-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.scss'],
})
export class RecipeListComponent implements OnInit {
  recipe: IRecipe | null = null;
  recipes: Recipe[] = [];
  account?: Account;

  constructor(
    protected dataUtils: JhiDataUtils,
    protected activatedRoute: ActivatedRoute,
    protected recipeService: RecipeService,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.recipeService.query().subscribe(
      (res: HttpResponse<IRecipe[]>) => this.onSuccess(res.body),
      () => this.onError()
    );
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

  onAccountSuccess(account: Account | undefined): void {
    if (account !== undefined) {
      this.account = account;
    }
  }

  private onError(): void {
    console.warn('There are no Recipes');
  }

  private onSuccess(body: Recipe[] | null): void {
    console.warn(body);
    this.recipes = body || [];
    this.recipe = (body || [])[0];
  }
}
