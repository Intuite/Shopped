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
import { IIngredient } from 'app/shared/model/ingredient.model';
import { CartService } from 'app/entities/cart/cart.service';
import { Account } from 'app/core/user/account.model';
import { ICartIngredient } from 'app/shared/model/cart-ingredient.model';
import { CollectionHasRecipeUpdateComponent } from 'app/entities/collection-has-recipe/collection-has-recipe-update.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ICollection } from 'app/shared/model/collection.model';
import { HttpResponse } from '@angular/common/http';
import { CollectionService } from 'app/entities/collection/collection.service';

interface FullIngredient {
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
  account!: Account;
  recipeTags!: IRecipeHasRecipeTag[] | null;
  ingredients: FullIngredient[] = [];
  collections: ICollection[] = [];

  constructor(
    protected dataUtils: JhiDataUtils,
    protected activatedRoute: ActivatedRoute,
    private accountService: AccountService,
    protected userService: UserService,
    protected recipeService: RecipeService,
    private cartService: CartService,
    protected modalService: NgbModal,
    protected ingredientService: IngredientService,
    protected collectionService: CollectionService
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
        this.account = account;
        this.userService.find(account.login).subscribe(user => (this.user = user));
      }
    });

    this.collectionService
      .queryAll({
        ...{ 'userId.equals': this.account?.id },
      })
      .subscribe((res: HttpResponse<ICollection[]>) => (this.collections = res.body || []));
  }

  addIngredientToCart(ing: IIngredient): void {
    if (this.account !== undefined) this.cartService.addIngredient(ing, this.account);
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

  addRecipeToCart(): void {
    if (this.account !== undefined && this.recipe !== undefined && this.recipe !== null) {
      const ning = this.ingredients.map(fi => {
        const ing: ICartIngredient = {
          id: fi.id,
          name: fi.name,
          amount: fi.amount,
        };
        return ing;
      });
      this.cartService.addRecipe(this.recipe, ning, this.account);
    }
  }

  create(collection: ICollection): void {
    const modalRef = this.modalService.open(CollectionHasRecipeUpdateComponent, { size: 'lg', backdrop: 'static', centered: true });
    modalRef.componentInstance.currentCollection = collection;
    modalRef.componentInstance.currentRecipe = this.recipe;
  }
}
