import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';
import { IPost } from 'app/shared/model/post.model';
import { IRecipe } from 'app/shared/model/recipe.model';
import { RecipeService } from 'app/entities/recipe/recipe.service';
import { IRecipeHasRecipeTag } from 'app/shared/model/recipe-has-recipe-tag.model';
import { IngredientService } from 'app/entities/ingredient/ingredient.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { CollectionHasRecipeUpdateComponent } from 'app/entities/collection-has-recipe/collection-has-recipe-update.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ICollection } from 'app/shared/model/collection.model';
import { CollectionService } from 'app/entities/collection/collection.service';
import { HttpResponse } from '@angular/common/http';

interface FullIngredient {
  id?: number;
  amount?: number;
  name?: string;
  imageContentType?: string;
  image?: any;
  unitAbbrev?: string;
}

@Component({
  selector: 'jhi-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.scss'],
})
export class PostDetailComponent implements OnInit {
  post: IPost | null = null;
  recipe: IRecipe | null | undefined;
  eventSubscriber?: Subscription;
  recipeTags!: IRecipeHasRecipeTag[] | null;
  ingredients: FullIngredient[] = [];
  account?: Account | undefined;
  collections: ICollection[] = [];

  constructor(
    protected recipeService: RecipeService,
    protected ingredientService: IngredientService,
    protected dataUtils: JhiDataUtils,
    protected activatedRoute: ActivatedRoute,
    protected modalService: NgbModal,
    protected accountService: AccountService,
    protected collectionService: CollectionService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ post }) => (this.post = post));
    this.accountService.identity().subscribe(res => (this.account = res || undefined));
    this.recipeService.query().subscribe(
      () => this.onSuccess(),
      () => this.onError()
    );
    this.collectionService.query().subscribe((res: HttpResponse<ICollection[]>) => (this.collections = res.body || []));
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

  onError(): void {
    console.warn('There was an error');
  }

  protected onSuccess(): void {
    this.recipeService.find(this.post?.recipeId).subscribe(response => {
      this.recipe = response.body;
      if (this.post?.recipeId !== undefined) {
        this.recipeService.findRecipeHasIngredients(this.post.recipeId).subscribe(recipeHasIng => {
          if (recipeHasIng.body !== null) {
            recipeHasIng.body.forEach(ing => {
              if (ing.id !== undefined) {
                this.ingredientService.find(ing.id).subscribe(fullIng => {
                  this.ingredients.push({
                    id: ing.ingredientId,
                    name: ing.ingredientName,
                    amount: ing.amount,
                    unitAbbrev: fullIng.body!.unitAbbrev,
                    image: fullIng.body?.image,
                    imageContentType: fullIng.body?.imageContentType,
                  });
                });
              }
            });
          }
        });
        this.recipeService.findRecipeHasRecipeTags(this.post?.recipeId).subscribe(recipeTags => (this.recipeTags = recipeTags.body));
      }
    });
  }

  create(collection: ICollection): void {
    const modalRef = this.modalService.open(CollectionHasRecipeUpdateComponent, { size: 'lg', backdrop: 'static', centered: true });
    modalRef.componentInstance.currentCollection = collection;
    modalRef.componentInstance.currentRecipe = this.recipe;
  }
}
