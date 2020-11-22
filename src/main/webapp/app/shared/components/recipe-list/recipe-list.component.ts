import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { HttpResponse } from '@angular/common/http';
import { Recipe, IRecipe } from 'app/shared/model/recipe.model';
import { RecipeService } from 'app/entities/recipe/recipe.service';
import { Account } from 'app/core/user/account.model';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-recipe-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.scss'],
})
export class RecipeListComponent implements OnInit {
  recipe = new Recipe();
  account?: Account;
  recipes: Recipe[] = [];
  @ViewChild('recipeList') recipeList!: RecipeListComponent;

  constructor(protected service: RecipeService, @Inject(MAT_DIALOG_DATA) public data: number, private accountService: AccountService) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(res => this.onAccountSuccess(res || undefined));

    this.service.query().subscribe(
      (res: HttpResponse<IRecipe[]>) => this.onSuccess(res.body),
      () => this.onError()
    );
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
