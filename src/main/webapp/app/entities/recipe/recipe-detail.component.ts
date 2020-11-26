import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';
import { Subscription } from 'rxjs';

import { IRecipe } from 'app/shared/model/recipe.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { AccountService } from 'app/core/auth/account.service';
import { IRecipeHasRecipeTag } from 'app/shared/model/recipe-has-recipe-tag.model';
import { IngredientService } from 'app/entities/ingredient/ingredient.service';
import { RecipeService } from 'app/entities/recipe/recipe.service';

export interface FullIngredient {
  id?: number;
  amount?: number;
  name?: string;
  imageContentType?: string;
  image?: any;
  unitAbbrev?: string;
}

@Component({
  selector: 'jhi-recipe-detail',
  templateUrl: './recipe-detail.component.html',
  styleUrls: ['./recipe-detail.component.scss'],
})
export class RecipeDetailComponent implements OnInit {
  recipe: IRecipe | null = null;
  eventSubscriber?: Subscription;
  statusOptions = ['ACTIVE', 'INACTIVE'];
  user!: IUser;
  recipeTags!: IRecipeHasRecipeTag[] | null;
  ingredients: FullIngredient[] = [];

  constructor(
    protected dataUtils: JhiDataUtils,
    protected activatedRoute: ActivatedRoute,
    private accountService: AccountService,
    protected userService: UserService,
    protected recipeService: RecipeService,
    protected ingredientService: IngredientService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recipe }) => {
      this.recipe = recipe;
      this.recipeService.findRecipeHasIngredients(recipe.id).subscribe(recipeHasIng => {
        if (recipeHasIng.body !== null) {
          recipeHasIng.body.forEach(ing => {
            if (ing.id !== undefined) {
              this.ingredientService.find(ing.id).subscribe(response => {
                this.ingredients.push({
                  id: ing.ingredientId,
                  name: ing.ingredientName,
                  amount: ing.amount,
                  unitAbbrev: response.body!.unitAbbrev,
                  image: response.body?.image,
                  imageContentType: response.body?.imageContentType,
                });
              });
            }
          });
        }
      });
      this.recipeService.findRecipeHasRecipeTags(recipe.id).subscribe(recipeTags => (this.recipeTags = recipeTags.body));
    });
    this.accountService.getAuthenticationState().subscribe(account => {
      if (account) {
        this.userService.find(account.login).subscribe(user => (this.user = user));
      }
    });
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
}
